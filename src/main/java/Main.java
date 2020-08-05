import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static GOT_API api = new GOT_API();

	public static void main(String[] args) {
		String characterNr = input();
		String characterURL = "https://anapioficeandfire.com/api/characters/" + characterNr;
		JSONObject character = api.getObject(characterURL);
		showCharacter(character);

		String houseUrl = getHouseUrl(character, "allegiances");
		JSONObject house = api.getObject(houseUrl);

		ArrayList<String> swornMembers = houseMembers(house);
		showMembers(swornMembers);

		String bookURL = "https://www.anapioficeandfire.com/api/books/";
		JSONArray allBooks = api.getArray(bookURL);
		ArrayList<JSONObject> books = getAllBooks(allBooks);
		String publisher = "Bantam Books";
		ArrayList<JSONObject> povCharacters = publishedBy(books, publisher);

		showPOVCharacters(publisher, povCharacters);
	}

	public static String input() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a character number: ");
		return scanner.next();
	}

	public static void showCharacter(JSONObject jsonObject) {
		System.out.println("-------------------------");
		System.out.println("Character name: " + jsonObject.get("name"));
		System.out.println("Born: " + jsonObject.get("born"));
		System.out.println("Gender: " + jsonObject.get("gender"));
		System.out.println("Title: " + jsonObject.get("titles"));
		System.out.println("Aliases: " + jsonObject.get("aliases"));
		System.out.println("Allegiences: " + jsonObject.get("allegiances"));
		System.out.println("Played by: " + jsonObject.get("playedBy"));
		System.out.println("Culture: " + jsonObject.get("culture"));
		System.out.println("-------------------------");
		System.out.println();
		System.out.println("Wait a minute....");
	}

	public static String getHouseUrl(JSONObject character, String allegiances) {
		String houseURL = character.get("allegiances").toString();
		houseURL = houseURL.substring(2, houseURL.length() - 2);
		return houseURL;
	}

	public static ArrayList<String> houseMembers(JSONObject houses) {
		ArrayList<String> swornMember = new ArrayList<>();
		String swornMemberURL = houses.get("swornMembers").toString();
		JSONArray swornMembersURL = new JSONArray(swornMemberURL);

		for (int i = 0; i < swornMembersURL.length(); i++) {
			String memberURL = swornMembersURL.getString(i);
			JSONObject member = api.getObject(memberURL);
			String members = member.get("name").toString();
			swornMember.add(members);
		}
		return swornMember;
	}

	public static void showMembers(ArrayList<String> swornMembers) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Show sworn members of this character's house [yes/no]? ");
		String in = scanner.next();
		if(in.equals("yes"))
				for (int i = 0; i < swornMembers.size(); i++) {
					System.out.println((i + 1) + ". " + swornMembers.get(i));
				}
	}

	public static ArrayList<String> newList(JSONObject json, String key){
		JSONArray jsonArray = json.getJSONArray(key);
		ArrayList<String> list = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(jsonArray.getString(i));
		}

		return list;
	}

	public static ArrayList<JSONObject> getAllBooks(JSONArray allBooks) {
		ArrayList<JSONObject> books = new ArrayList<>();
		for (int i = 0; i < allBooks.length(); i++) {
			books.add(allBooks.getJSONObject(i));
		}
		return books;
	}

	public static ArrayList<JSONObject> publishedBy(ArrayList<JSONObject> books, String publisher){
		ArrayList<JSONObject> bantamBooks = new ArrayList<>();
		for (JSONObject book: books) {
			if(book.get("publisher").equals(publisher)){
				bantamBooks.add(book);
			}
		}
		return bantamBooks;
	}

	public static void showPOVCharacters(String publisher, ArrayList<JSONObject> povCharacters){
		System.out.println("*****************************************************************");
		System.out.println("Showing POV characters by books with publisher " + publisher);
		System.out.println("*****************************************************************");
		for (JSONObject publishersBook: povCharacters) {
			System.out.println(publishersBook.get("name"));
			System.out.println();

			ArrayList<String> povCharacter = newList(publishersBook, "povCharacters");

			for (String povChar: povCharacter) {
				JSONObject jsonChar = api.getObject(povChar);
				System.out.println(jsonChar.get("name"));
			}
			System.out.println("**************************************************************");
		}
	}

}
