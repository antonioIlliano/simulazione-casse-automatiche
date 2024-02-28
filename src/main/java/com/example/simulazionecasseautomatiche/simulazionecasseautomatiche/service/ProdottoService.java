package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.service;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.ProdottoDTO;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request.ProdottoRequest;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response.ProdottoResponse;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.*;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.*;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository repository;

    @Autowired
    private BarCodeRepository barCodeRepository;

    @Autowired
    private UDMRepository udmRepository;

    @Autowired
    private RepartoRepository repartoRepository;

    @Autowired
    private ScontrinoRepository scontrinoRepository;

    @Autowired
    private DettaglioScontrinoRepository dettaglioRepository;


    public ProdottoDTO getProdottoByNome(String nomeProdotto) throws Exception {

        Prodotto prodotto = repository.findByNomeProdottoIgnoreCase(nomeProdotto);

        if (Objects.isNull(prodotto)) {
            throw new Exception("Product Not Found");
        }

        List<BarCode> barCodesList = barCodeRepository.findByProductId(prodotto);

        List<String> barCodes = new ArrayList<>();
        if (!barCodesList.isEmpty()) {
            barCodesList.forEach((b) -> barCodes.add(b.getCode()));
        }

        return ProdottoDTO.builder()
                .nomeProdotto(prodotto.getNomeProdotto())
                .grammatura(prodotto.getGrammatura())
                .udm(prodotto.getUdm().getCodiceUdm())
                .reparto(prodotto.getReparto().getCodiceReparto())
                .prezzo(prodotto.getPrezzo())
                .barCodeList(barCodes)
                .build();


    }

    public ProdottoResponse createProduct(ProdottoRequest prodottoRequest) throws Exception {

        Prodotto check = repository.findByNomeProdottoIgnoreCase(prodottoRequest.getNomeProdotto());
        if (Objects.nonNull(check)) {
            throw new Exception("Product already exist");
        }

        UnitaDiMisura udm = udmRepository.findByCodiceUdmIgnoreCase(prodottoRequest.getUdm());
        if (Objects.isNull(udm)) {
            throw new Exception("UDM doesn't exist");
        }

        Reparto reparto = repartoRepository.findByCodiceRepartoIgnoreCase(prodottoRequest.getReparto());
        if (Objects.isNull(reparto)) {
            throw new Exception("Department doesn't exist");
        }

        Prodotto prodotto = Prodotto.builder()
                .nomeProdotto(prodottoRequest.getNomeProdotto())
                .grammatura(prodottoRequest.getGrammatura())
                .reparto(reparto)
                .udm(udm)
                .prezzo(prodottoRequest.getPrezzo())
                .stock(prodottoRequest.getStock())
                .build();

        repository.save(prodotto);


        return ProdottoResponse.builder()
                .nomeProdotto(prodotto.getNomeProdotto())
                .reparto(prodotto.getReparto().getCodiceReparto())
                .udm(prodotto.getUdm().getCodiceUdm())
                .prezzo(prodotto.getPrezzo())
                .response(Response.SC_OK)
                .build();

    }

    public List<ProdottoDTO> getAllProducts() {

        List<Prodotto> prodotti = repository.findAll();

        List<ProdottoDTO> prodottiList = new ArrayList<>();

        List<BarCode> barCodes = new ArrayList<>();

        for (Prodotto p: prodotti){
            barCodes = barCodeRepository.findAllByProductId(p);
            prodottiList.add(ProdottoDTO.builder()
                            .nomeProdotto(p.getNomeProdotto())
                            .barCodeList(barCodes.stream().map(BarCode::getCode).toList())
                            .prezzo(p.getPrezzo())
                            .reparto(p.getReparto().getCodiceReparto())
                            .udm(p.getUdm().getCodiceUdm())
                            .grammatura(p.getGrammatura())
                    .build());
        }

        return prodottiList;

    }


    private ProdottoDTO mapToProduct(Prodotto prodotto) {

        List<BarCode> barCodesList = barCodeRepository.findByProductId(prodotto);

        List<String> barCodes = new ArrayList<>();

        if (barCodesList.isEmpty()) {
            barCodesList.forEach((b) -> barCodes.add(b.getCode()));
        }

        return ProdottoDTO.builder()
                .nomeProdotto(prodotto.getNomeProdotto())
                .grammatura(prodotto.getGrammatura())
                .udm(prodotto.getUdm().getCodiceUdm())
                .reparto(prodotto.getReparto().getCodiceReparto())
                .prezzo(prodotto.getPrezzo())
                .barCodeList(barCodes)
                .build();
    }

    public Map<String, Long> calcolaStock(LocalDate date) {

        List<Prodotto> prodottiList = repository.findAll();

        List<Scontrino> scontrini = scontrinoRepository.findAllByDataDiAcquisto(date);

        Map<String, Long> response = new HashMap<>();
        for (Prodotto p : prodottiList) {
            for (Scontrino s : scontrini) {
                for (DettaglioScontrino d : s.getDettaglioScontrino()) {
                    if (p.getId().equals(d.getProdottoId()) && !s.getContabilizzato()) {
                        p.setStock(p.getStock() - d.getQuantita());
                    }
                }

            }
            response.put(p.getNomeProdotto(), p.getStock());

            repository.save(p);
        }
        for (Scontrino scontrino : scontrini) {
            scontrino.setContabilizzato(true);
            scontrinoRepository.save(scontrino);
        }

        return response;

    }

    public Map<String, Float> getImportoByReparto(LocalDate data) {

        Map<String, Float> map = new HashMap<>();

        List<Scontrino> scontrini = scontrinoRepository.findAll().stream().filter(s-> s.getDataDiAcquisto().equals(data)).toList();
        for (Scontrino s : scontrini) {
            for (DettaglioScontrino d : s.getDettaglioScontrino()) {
                Prodotto prodotto = repository.findById(d.getProdottoId()).orElseThrow();
                if (!map.containsKey(prodotto.getReparto().getNomeReparto())) {
                    map.put(prodotto.getReparto().getNomeReparto(), d.getPrezzo());
                } else {
                    var numero = map.get(prodotto.getReparto().getNomeReparto());
                    map.put(prodotto.getReparto().getNomeReparto(), numero + d.getPrezzo());
                }
            }
        }

        List<Reparto> reparti = repartoRepository.findAll();
        for (Reparto r : reparti) {
            if (!map.containsKey(r.getNomeReparto())) {
                map.put(r.getNomeReparto(), 0F);
            }
        }

        return map;
    }

    public Map<String, Float> getImportoAnnuoByReparto(Integer anno) {

        Map<String, Float> map = new HashMap<>();

        List<Scontrino> scontrini = scontrinoRepository.findAllByYear(anno);
        for (Scontrino s : scontrini) {
            for (DettaglioScontrino d : s.getDettaglioScontrino()) {
                Prodotto prodotto = repository.findById(d.getProdottoId()).orElseThrow();
                if (!map.containsKey(prodotto.getReparto().getNomeReparto())) {
                    map.put(prodotto.getReparto().getNomeReparto(), d.getPrezzo());
                } else {
                    var numero = map.get(prodotto.getReparto().getNomeReparto());
                    map.put(prodotto.getReparto().getNomeReparto(), numero + d.getPrezzo());
                }
            }
        }

        List<Reparto> reparti = repartoRepository.findAll();
        for (Reparto r : reparti) {
            if (!map.containsKey(r.getNomeReparto())) {
                map.put(r.getNomeReparto(), 0F);
            }
        }

        return map;
    }

    public ProdottoDTO updatePrice(Long id, Float price) {

        Prodotto prodotto = repository.findById(id).orElseThrow();

        prodotto.setPrezzo(price);
        repository.save(prodotto);

        return ProdottoDTO.builder()
                .grammatura(prodotto.getGrammatura())
                .nomeProdotto(prodotto.getNomeProdotto())
                .udm(prodotto.getUdm().getCodiceUdm())
                .reparto(prodotto.getReparto().getCodiceReparto())
                .prezzo(prodotto.getPrezzo())
                .build();
    }
}
