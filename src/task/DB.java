package task;

import java.util.Hashtable;

import java.util.Set;

public class DB {
	private static DB instance = null;
	private static Hashtable<String, Integer> ht = new Hashtable();

	public static DB getInstance() {
		if (instance == null)
			instance = new DB();
		return instance;
	}

	public boolean proceed(String a, String req) {
		Integer status = ht.get(a);
		switch (req) {
		case "RESERVATION":
			if (status == 1) {
				status++;
				ht.remove(a);
				ht.put(a, status);
				return true;
			}
			break;
		case "PAID":
			if (status == 2) {
				status++;
				ht.remove(a);
				ht.put(a, status);
				return true;
			}
			break;

		default:
			return false;
			
		}

		return false;
	}

	public boolean exists(String a) {
		boolean ret=false;
		try{ ret=ht.contains(a);
		}catch (NullPointerException ex){
			ret=false;
		}
		return ret;
	}

	public void addTrip(String a, Integer status) {
		ht.put(a, status);
	}

	public String print(String a) {
		String ret = "Cookies: " + a + " status: ";
		switch (ht.get(a)) {
		case 0:
			ret += "NONE";
			break;
		case 1:
			ret += "NEW";
			break;
		case 2:
			ret += "WAITING FOR PAYMENT";
			break;
		case 3:
			ret += "COMPLETED";
			break;
		}

		return ret;
	}

	public String printAll() {
		String ret="";
		Set<String> keys = ht.keySet();
		for (String key : keys) {
			ret+= key + " status: " + ht.get(key).toString() + "\n"; 
		}
		return ret;

	}
}
