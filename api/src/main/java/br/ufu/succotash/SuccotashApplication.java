package br.ufu.succotash;


import br.ufu.succotash.domain.enumeration.TableStatus;
import br.ufu.succotash.domain.model.Item;
import br.ufu.succotash.domain.model.Restaurant;
import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.repository.ItemRepository;
import br.ufu.succotash.repository.RestaurantRepository;
import br.ufu.succotash.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
@EnableWebMvc
@SpringBootApplication
@RequiredArgsConstructor

public class SuccotashApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuccotashApplication.class, args);
	}

	private final RestaurantRepository restaurantRepository;

	private final TableRepository tableRepository;
	private final ItemRepository itemRepository;
	@Bean
	public void init(){


		Restaurant restaurant = restaurantRepository.save(Restaurant.builder()
													.name("Restaurant 1")
													.build());

		for(int i = 1; i <= 20; i++) {
			Table table = Table.builder()
					.name("Table "+i)
					.urlQrCode("https://www.google.com/"+ i)
					.restaurant(restaurant)
					.status(TableStatus.OPEN)
					.build();
			tableRepository.save(table);
		}

		for(int i = 1; i <= 20; i++) {
			Item item = Item.builder()
					.image(i+".jpg")
					.name("Item "+i)
					.description("Description "+i)
					.price(new BigDecimal(i))
					.restaurant(restaurant)
					.build();
			itemRepository.save(item);
		}


	}

}
