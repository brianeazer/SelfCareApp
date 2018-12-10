package co.grandcircus.selfcareapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import co.grandcircus.selfcareapp.Dao.LikeDao;
import co.grandcircus.selfcareapp.Entity.GfyItem;
import co.grandcircus.selfcareapp.Entity.User;
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
		// two
		// seperate tags on the off chance that there is a large difference in
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
		List<UserLikes> top10 = new ArrayList<>();
		int count = 0;
		while (count < 10) {
			top10.add(likes.get(likes.size() - 1 - count));
			count++;
		}
		return top10;
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
}
