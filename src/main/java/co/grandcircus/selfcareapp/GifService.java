package co.grandcircus.selfcareapp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.grandcircus.selfcareapp.Dao.LikeDao;
import co.grandcircus.selfcareapp.Entity.GfyItem;
import co.grandcircus.selfcareapp.Entity.User;
import co.grandcircus.selfcareapp.Entity.UserEmotion;
import co.grandcircus.selfcareapp.Entity.UserLikes;
import co.grandcircus.selfcareapp.apiservice.ApiService;

@Component
public class GifService {

	@Autowired
	LikeDao likeDao;

	@Autowired
	ApiService apiService;

	public int randomInteger(int max) {
		int num = (int) Math.floor(Math.random() * max);
		return num;
	}

	public List<GfyItem> getGifList(List<UserLikes> topLikes) {
		String tag1;
		String tag2;
		// tagRequests is to prevent the app from slowing down by taking forever to find
		// two separate tags on the off chance that there is a large difference in
		// probabilities
		int tagRequests = 0;

		// Here we get two tags so we can make more specific api calls
		do {
			UserLikes ul1 = weightedProbability(topLikes);
			UserLikes ul2 = weightedProbability(topLikes);
			tag1 = ul1.getTag();
			tag2 = ul2.getTag();
			tagRequests++;
		} while (tag1.equals(tag2) || tagRequests <= 10);

		String tag;
		if (!tag1.equals(tag2)) {
			tag = tag1 + "+" + tag2;
		} else {
			tag = tag1;
		}

		// gets list of gifs based on chosen tags
		List<GfyItem> gfyItems = apiService.options(tag, 50).getGfycats();

		if (gfyItems.size() == 0) {
			getGifList(topLikes);
		}

		return gfyItems;
	}

	public UserLikes weightedProbability(List<UserLikes> top10) {
		int totalSum = 0;

		for (UserLikes tag : top10) {
			totalSum = totalSum + tag.getCount();
		}
		int index = randomInteger(totalSum);
		int sum = 0;
		int i = 0;

		while (sum < index) {
			sum = sum + top10.get(i++).getCount();
		}
		return top10.get(Math.max(0, i - 1));
	}

	public List<UserLikes> getTopLikes(List<UserLikes> likes) {
		Collections.sort(likes, (l1, l2) -> l1.getCount().compareTo(l2.getCount()));
		List<UserLikes> topLikes = new ArrayList<>();
		int count = 0;
		while (count < 10) {
			topLikes.add(likes.get(likes.size() - 1 - count));
			count++;
		}
		return topLikes;
	}
	
	public Map<String, List<String>> categoryMap() {
		Map<String, List<String>> categories = new HashMap<>();
		
		categories.put("Food", Arrays.asList("recipe, food", "foodnetwork", "lunch", "meal", "koreanbbq", "bbq", "cook",
				"dessert", "breakfast", "dinner"));
		categories.put("Cartoons",
				Arrays.asList("cartoonnetwork","spongebob", "nickelodeon", "boomerang", "nickjr", "cartoonmovie"));
		categories.put("Holidays", Arrays.asList("happyholidays", "christmas", "thanksgiving", "festive", "holidays",
				"christmascards", "merrychristmas"));
		categories.put("Cats", Arrays.asList("kittens", "cute kittens", "cats, aww", "cats", "cat", "meow"));
		categories.put("Sports", Arrays.asList("sports", "sport", "basketball", "football", "soccer", "hockey",
				"baseball", "rugby", "volleyball", "golf", "tennis"));
		categories.put("Fails", Arrays.asList("fail", "epicfail", "fails", "accident"));
		categories.put("Nature",
				Arrays.asList("waterfalls",  "forest, aesthetic", "forest, relaxing", "forest, ASMR"));
		categories.put("Horror Movie Culture", Arrays.asList("Nightmareonelmstreet", "Texaschainsaw", "leatherface",
				"horrorfilm", "chucky", "horroredit"));
		categories.put("All Movie Culture",
				Arrays.asList("movies", "movie", "kidmovies", "Indiefilms", "animatedmovie"));
		categories.put("Gaming",
				Arrays.asList("gaming", "xbox", "playstation", "wii", "esports", "snes", "atari", "gamer", "pcgaming",
						"csgo", "cod", "system", "xboxdvr", "carepackage", "sharefactory", "killstreak", "ps4share"));
		categories.put("Anime", Arrays.asList("manga", "dbz", "deathnote", "anime", "naruto"));

		categories.put("Chill", Arrays.asList("lofi", "chillwave", "meditation", "relaxing"));

		categories.put("Your Top Ten", Arrays.asList(""));
		
		return categories;
	}

	public void updateUserLikeTable(String tag, User user, Integer count) {
		UserLikes userLike = likeDao.getUserLikes(user, tag);
		if (userLike == null) {
			userLike = new UserLikes();
			userLike.setCount(count);
			userLike.setTag(tag);
			userLike.setUser(user);
			likeDao.createUserLike(userLike);
		} else {
			Integer likes = userLike.getCount();
			likes += count;
			userLike.setCount(likes);
			likeDao.update(userLike);
		}

	}
	
	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

	public ArrayList<Double> getAverageMoodRating(Map<LocalDate, List<UserEmotion>> daysOfWeek) {
		ArrayList<Double> averageMoodRatings = new ArrayList<>();
		// ld gets the list of UserEmotions for that day
		for (Entry<LocalDate, List<UserEmotion>> ld: daysOfWeek.entrySet()) {
			Double sum = 0.0;
			Integer count = 0;
			for (UserEmotion ue : ld.getValue()) {
				System.out.println(ue.getEmotionRating());
				count++;
				sum += ue.getEmotionRating();
			}
			Double averageMoodRating = sum/count;
			averageMoodRatings.add(averageMoodRating);
		}
		return averageMoodRatings;
	}

	public ArrayList<String> getTopCategories(Map<LocalDate, List<UserEmotion>> daysOfWeek) {
		ArrayList<String> topCategories = new ArrayList<>();
		for (Entry<LocalDate, List<UserEmotion>> ld: daysOfWeek.entrySet()) {
			HashMap<String, Integer> topCategoriesByDay = new HashMap<>();
			for (UserEmotion ue : ld.getValue()) {
				if (topCategoriesByDay.containsKey(ue.getCategory())== false){
					topCategoriesByDay.put(ue.getCategory(), 1);
				} else {
					Integer currentValue = topCategoriesByDay.get(ue.getCategory());
					currentValue++;
					topCategoriesByDay.put(ue.getCategory(), currentValue);
				}
			}
			Map.Entry<String, Integer> maxEntry = null;

			for (Map.Entry<String, Integer> cat : topCategoriesByDay.entrySet()) {
			    if (maxEntry == null || cat.getValue().compareTo(maxEntry.getValue()) > 0)
			    {
			        maxEntry = cat;
			       
			    }
			}
			topCategories.add(maxEntry.getKey());
		}
		return topCategories;
	}

}
