package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche;

import com.mysql.cj.jdbc.JdbcConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories
public class SimulazioneCasseAutomaticheApplication {
	@Value("{spring.datasource.url}")
	String url;
	@Value("{spring.datasource.username}")
	String username;
	@Value("{spring.datasource.password}")
	String password;
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SimulazioneCasseAutomaticheApplication.class, args);

		Runner runner = new Runner();
		runner.run();
	}
	public static class Runner implements CommandLineRunner{
		@Override
		public void run(String... args) throws Exception {

			DataSource dataSource = DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/esercizio").username("root").password("root").build();
			try(Connection connection = dataSource.getConnection()){
				String sqlInsertReparti = """
						INSERT INTO esercizio.reparto_enum
						(id, nome_reparto, reparto_code, prodotti) VALUES
						(1, 'ORTOFRUTTA', 'ORTOFRUTTA', NULL),
						(2, 'SALUMERIA', 'SALUMERIA', NULL),
						(3, 'PESCHERIA', 'PESCHERIA', NULL),
						(4, 'ELETTRONICA','ELETTRONICA', NULL),
						(5, 'DETERSIVI', 'DETERSIVI', NULL),
						(6, 'CANCELLERIA', 'CANCELLERIA', NULL);
				""";

				PreparedStatement preparedStatementReparti = connection.prepareStatement(sqlInsertReparti);
				preparedStatementReparti.executeUpdate();


				String sqlInsertUDM = """
				INSERT INTO esercizio.unita_di_misura_enum
				(udm_id, udm_code, udm_nome) VALUES
				(1, 'kilogrammi', 'kilogrammi'),
				(2, 'litri', 'litri'),
				(3, 'pezzo', 'pezzo'),
				(4, 'etto', 'etto');
				""";
				PreparedStatement preparedStatementudm = connection.prepareStatement(sqlInsertUDM);
				preparedStatementudm.executeUpdate();

			}catch (SQLException sqlException){
				sqlException.printStackTrace();
			}
		}
	}

}
