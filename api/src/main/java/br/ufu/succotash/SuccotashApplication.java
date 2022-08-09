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

import java.math.BigDecimal;
import java.util.ArrayList;

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

	/*
	{
 "order":{
      "user": { "id": "ed4a9784-575b-4fca-adf6-0c135954a5ef" },
      "table": { "id": "3ed4e9a9-9964-44ad-ad78-e5954d378ca5"}
 },
 "items":[
          { "id": "2567585e-63e5-4368-8847-5263ecee8c9a"},
          { "id": "5e42bbe1-d7f6-4cbb-9f03-66f09f0ea5e9"},
          { "id": "8712b57e-9a74-4950-a22c-ef26c546da34"},
          { "id": "66261ca1-0d0c-4e40-8519-0ff44b421ace"}
      ],
 "quantities":[
     1,2,3,4
 ]
}
	 */

}
