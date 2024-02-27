package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.service;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.QuantitaIncassoDTO;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request.CreazioneScontrinoRequest;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response.CreazioneScontrinoResponse;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.BarCode;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.DettaglioScontrino;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Prodotto;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Scontrino;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.BarCodeRepository;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.DettaglioScontrinoRepository;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.ProdottoRepository;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.ScontrinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScontrinoService {

    @Autowired
    private BarCodeRepository barCodeRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private ScontrinoRepository repository;

    @Autowired
    private DettaglioScontrinoRepository dettaglioRepo;

    public CreazioneScontrinoResponse createScontrino(CreazioneScontrinoRequest request) throws Exception {

        Scontrino scontrino = new Scontrino();
        scontrino.setCodiceScontrino(request.getCodiceScontrino());
        scontrino.setDataDiAcquisto(LocalDate.now());
        scontrino.setContabilizzato(false);

        List<Long> prodottiIds = barCodeRepository.findAllIdsByBarCodeIn(request.getBarCodeProdotti());

        Map<String, Float> map = new HashMap<>();

        for(Long id: prodottiIds){
            Prodotto prodotto = prodottoRepository.findById(id).orElseThrow(()-> new RuntimeException());
            prodotto.setStock(prodotto.getStock()-1);
            if(!map.containsKey(prodotto.getNomeProdotto())) {
                map.put(prodotto.getNomeProdotto(), prodotto.getPrezzo());
            }else{
                Float prezzo = map.get(prodotto.getNomeProdotto());
                prezzo = prezzo + prodotto.getPrezzo();
                map.put(prodotto.getNomeProdotto(), prezzo);
            }

        }
        var prezzoTotale = 0F;

        for (Float p: map.values()){
            prezzoTotale = prezzoTotale + p;
        }
        scontrino.setTotaleScontrino(prezzoTotale);

        List<DettaglioScontrino> dettaglioScontrinoList = new ArrayList<>();
        for (String s: map.keySet()){
           Prodotto prodotto = prodottoRepository.findByNomeProdottoIgnoreCase(s);
           dettaglioScontrinoList.add(DettaglioScontrino.builder()
                   .scontrinoId(scontrino)
                    .prodottoId(prodotto.getId())
                    .prezzo(map.get(prodotto.getNomeProdotto()))
                    .quantita(prodottiIds.stream().filter(p-> p.compareTo(prodotto.getId())== 0).count())
                    .build());
        }
        scontrino.setDettaglioScontrino(dettaglioScontrinoList);
        repository.save(scontrino);
        dettaglioRepo.saveAll(dettaglioScontrinoList);


        return CreazioneScontrinoResponse.builder()
                .prodotti(map)
                .totale(prezzoTotale)
                .build();
    }


    private Map<Long,Integer> countRecursiveStrings(List<Long> prodottiIds){

        Map<Long, Integer> recursiveStrings = new HashMap<>();

        prodottiIds.forEach(b->{
            var conteggio = recursiveStrings.getOrDefault(b, 0);
            recursiveStrings.put(b, conteggio + 1);
        });

       return recursiveStrings;
    }


    public Float getTotaleByDay(LocalDate data) {

        List<Scontrino> scontrini = repository.findAllByDataDiAcquisto(data);

         var totaleIncassi = 0F;

        for (Scontrino scontrino: scontrini) {
            totaleIncassi = totaleIncassi+scontrino.getTotaleScontrino();
        }
              return totaleIncassi;

    }

    public List<QuantitaIncassoDTO> getQuantitaAndIncasso(LocalDate data) {


        List<QuantitaIncassoDTO> quantitaIncassoList = null;

        List<Prodotto> prodotti = prodottoRepository.findAll();

        List<Scontrino> scontrini = repository.findAllByDataDiAcquisto(data);


        for(Scontrino s:scontrini){
            for(DettaglioScontrino d: s.getDettaglioScontrino()){

                Prodotto prodotto = prodottoRepository.findById(d.getProdottoId()).orElseThrow();
                if(quantitaIncassoList == null){
                    quantitaIncassoList = new ArrayList<>();
                    quantitaIncassoList.add(QuantitaIncassoDTO.builder()
                            .totaleIncasso(d.getPrezzo())
                            .nomeProdotto(prodotto.getNomeProdotto())
                            .quantita(d.getQuantita())
                            .build());
                    continue;
                }
                for (QuantitaIncassoDTO q: quantitaIncassoList){
                    if (q.getNomeProdotto().equals(prodotto.getNomeProdotto())){
                        q.setQuantita(q.getQuantita() +d.getQuantita());
                        q.setTotaleIncasso(q.getTotaleIncasso() + d.getPrezzo());
                    }else{
                        quantitaIncassoList.add(QuantitaIncassoDTO.builder()
                                        .totaleIncasso(d.getPrezzo())
                                        .nomeProdotto(prodotto.getNomeProdotto())
                                        .quantita(d.getQuantita())
                                .build());

                    }
                    break;
                }
            }
        }

        for (Prodotto p: prodotti){
            boolean found = false;
            for(QuantitaIncassoDTO q: quantitaIncassoList){
                if(p.getNomeProdotto().equals(q.getNomeProdotto())) {
                found = true;
                break;
                }
            }
            if(!found){
                quantitaIncassoList.add(QuantitaIncassoDTO.builder()
                        .totaleIncasso(0F)
                        .nomeProdotto(p.getNomeProdotto())
                        .quantita(0L)
                        .build());
            }
        }

        return quantitaIncassoList;
    }
}
