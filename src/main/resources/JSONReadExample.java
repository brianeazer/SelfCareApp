
public class JSONReadExample {
	public static void main(String[] args) throws Exception  { 
		// parsing file "JSONExample.json" 
		Object obj = new JSONParser().parse(new FileReader("JSONExample.json")); 
		          
		// typecasting obj to JSONObject 
		JSONObject jo = (JSONObject) obj; 
		          
		// get gifUrl and tags
		String gifUrl = (String) jo.get("gifUrl"); 
		List<String> tags = (String) jo.get("tags"); 
		          
		System.out.println(gifUrl);
		System.out.println(tags);
		          
		// iterating phoneNumbers 
		Iterator itr2 = ja.iterator(); 
		          
		while (itr2.hasNext()) {
			itr1 = ((Map) itr2.next()).entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				System.out.println(pair.getKey() + " : " + pair.getValue()); 
		    } 
		} 
	} 
}
