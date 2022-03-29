package br.com.bcp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication
public class App implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterjdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Clean up
		jdbcTemplate.update("delete from customer_tst where id <= ?", 5);

		// Split up the array of whole names into an array of first/last names
		List<Object[]> splitUpNames = Arrays.asList("1 John Woo", "2 Jeff Dean", "3 Josh One", "4 Josh Two", "5 Josh Three").stream()
				.map(name -> name.split(" ")).collect(Collectors.toList());

		// Use a stream to print out each tuple of the list
		splitUpNames.forEach(
				name -> log.info(String.format("Inserting customer record for %s %s %s", name[0], name[1], name[2])));

		// Uses JdbcTemplate's batchUpdate operation to bulk load data
		jdbcTemplate.batchUpdate("INSERT INTO customer_tst(id, first_name, last_name) VALUES (?,?,?)", splitUpNames);

		log.info("Querying for customer records where first_name = 'Josh':");
		jdbcTemplate
				.queryForList(
					"SELECT id, first_name, last_name FROM customer_tst WHERE first_name = ? ",
					"Josh")
						.forEach(customer -> log.info(customer.toString()));
		
		log.info("Querying for customer records where first_name = 'Josh' and last_name in ('One', 'Two')");
		
		String query = "SELECT * FROM customer_tst WHERE first_name = :fn";
		query = query + " and last_name in (:ln)";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("fn", "Josh");
		parameters.addValue("ln", Arrays.asList("Two", "Three", "One"));

		namedParameterjdbcTemplate
				.queryForList(
					query, parameters)
						.forEach(customer -> log.info(customer.toString()));
		
	}

}
