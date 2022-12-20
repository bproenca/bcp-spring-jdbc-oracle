package br.com.bcp;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class App implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Clean up
		jdbcTemplate.update("delete from customer_tst where id > ?", 0);

		String sqlInsert = "INSERT INTO customer_tst(FIRST_NAME, LAST_NAME, BIRTHDAY, SALARY) VALUES (?,?,?,?)";
		jdbcTemplate.update(sqlInsert, "Maria", "Silva", LocalDate.of(1970, 3, 15), 2000);
		jdbcTemplate.update(sqlInsert, "Jose", "Almeida", LocalDate.of(2012, 10, 25), 5000);

		log.info("Querying all customers");

		jdbcTemplate
				.queryForList(
						"SELECT * FROM customer_tst")
				.forEach(row -> {
					row.entrySet().forEach(field -> log.info("Column: {} Type: {} Values: {}", 
							field.getKey(), field.getValue().getClass(), field.getValue()
						));
				});
	}

}
