import java.awt.geom.Point2D;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Graph {
	private City destination;
	private City origin;
	private ArrayList<City> route;
	private ArrayList<Attraction> attractions;
	private Hashtable<String, City> cities;
	private double cost;
	final int degreesToKmConversion = 111;

	public Graph() {
		cities = new Hashtable<String, City>();

//		String[] cityNames = {"Milford Sound" ,"Cape Reinga" ,"Collingwood" ,"Takaka","Pukenui","Mangonui","Kaitaia" ,"Kerikeri" ,
//				"Paihia" ,"Kaikohe" ,"Whangarei" ,"Opononi" ,"Dargaville" ,"Wellsford" ,"Great Barrier Is." ,"Ruawai" ,"Tauhoa" ,
//				"Helensville" ,"Auckland" ,"Warkworth" ,"Orewa" ,"Coromandel" ,"Waiheke Is." ,"Thames" ,"Papakura" ,"Pukekohe" ,
//				"Huntly" ,"Hamilton" ,"Raglan" ,"Otorohanga" ,"Kawhia" ,"Te Kuiti" ,"Piopio" ,"Mokau" ,"Waitara" ,"Whitianga" ,"Whangamata" ,
//				"Waihi" ,"Paeroa" ,"Tatuanui" ,"Tauranga" ,"Cambridge" ,"Te Puke" ,"Putaruru" ,"Tokoroa" ,"Kuratau Junction","Edgecumbe" ,
//				"Waihau Bay" ,"Te Kaha" ,"Whakatane" ,"Rotorua" ,"Murupara" ,"Tuai" ,"Opotiki" ,"Matawai" ,"Te Araroa" ,"Ruatoria" ,"Tokomaru Bay",
//				"Tolaga Bay" ,"Taumarunui" ,"Ohura" ,"Taupo" ,"New Plymouth" ,"Okato","Opunake" ,"Hawera" ,"Picton" ,"Rangitaiki" ,"Gisborne",
//				"Wairoa" ,"Tutira" ,"National Park" ,"Stratford" ,"Raetihi" ,"Waiouru" ,"Turangi" ,"Tikokino" ,"Patea" ,"Whanganui" ,"Bulls",
//				"Foxton" ,"Levin" ,"Otaki" ,"Paraparaumu" ,"Napier" ,"Hastings" ,"Waipawa" ,"Waipukurau" ,"Hunterville" ,"Feilding" ,"Porirua",
//				"Upper Hutt" ,"Lower Hutt" ,"Dannevirke" ,"Woodville" ,"Palmerston North" ,"Eketahuna" ,"Masterton" ,"Featherston" ,
//				"Martinborough", "Westport" ,"Karamea" ,"Wakefield" ,"Murchison" ,"Reefton" ,"Springs Junction" ,"Motueka" ,"Nelson",
//				"Wellington" ,"Blenheim" ,"Seddon" ,"Ward" ,"Kaikoura" ,"Greymouth" ,"Hanmer Springs" ,"St Arnaud" ,"Cheviot",
//				"Haast" ,"Wanaka" ,"Hokitika" ,"Franz Josef" ,"Fox Glacier" ,"Mount Cook" ,"Lake Tekapo","Fairlie" ,"Twizel" ,"Kumara Junction",
//				"Arthur's Pass" ,"Springfield" ,"Darfield" ,"Methven" ,"Rolleston" ,"Greta Valley" ,"Amberley" ,"Woodend" ,"Christchurch" ,
//				"Akaroa" ,"Geraldine" ,"Ashburton" ,"Omarama" ,"Kurow" ,"Ranfurly" ,"Temuka" ,"Timaru" ,"Waimate" ,"Oamaru" ,"Queenstown" ,
//				"Kingston" ,"Te Anau" ,"Clifden","Lumsden" ,"Cromwell" ,"Alexandra" ,"Palmerston" ,"Dunedin" ,"Mosgiel" ,"Gore",
//				"Milton" ,"Winton" ,"Balclutha" ,"Edendale" ,"Riverton" ,"Invercargill" ,"Bluff" ,"Oban" };
//
//		String[][] linkedNames = {
//				{"Te Anau"},{"Pukenui"},{"Takaka"},{"Collingwood", "Motueka"},{"Cape Reinga", "Kaitaia"},{"Kaitaia", "Kerikeri"},{"Pukenui", "Mangonui", "Paihia"},
//				{"Mangonui", "Paihia"},{"Kerikeri", "Kaikohe", "Whangarei", "Kaitaia"},{"Paihia", "Opononi"},{"Paihia", "Dargaville", "Wellsford"},{"Kaikohe", "Dargaville"},
//				{"Opononi", "Ruawai", "Whangarei"},{"Ruawai", "Whangarei", "Tauhoa", "Warkworth"},{"Waiheke Is."},{"Dargaville", "Wellsford"},{"Wellsford", "Helensville"},{"Tauhoa", "Auckland"},
//				{"Helensville", "Orewa", "Papakura", "Waiheke Is."},{"Wellsford", "Orewa"},{"Warkworth", "Auckland"},{"Whitianga", "Thames"},{"Auckland", "Great Barrier Is."},
//				{"Coromandel", "Paeroa"},{"Auckland", "Pukekohe"},{"Papakura", "Paeroa", "Huntly", "Tatuanui"},{"Pukekohe", "Hamilton"},{"Huntly", "Raglan", "Tatuanui", "Cambridge", "Otorohanga"},
//				{"Hamilton"},{"Kawhia", "Hamilton", "Te Kuiti", "Rotorua"},{"Otorohanga"},{"Otorohanga", "Piopio", "Taumarunui"},{"Te Kuiti", "Mokau"},{"Piopio", "Waitara"},{"Mokau", "New Plymouth"},
//				{"Coromandel", "Whangamata"},{"Whitianga", "Waihi"},{"Paeroa", "Tauranga", "Whangamata"},{"Pukekohe", "Tatuanui", "Waihi", "Thames"},{"Pukekohe", "Paeroa", "Hamilton", "Cambridge"},
//				{"Waihi", "Putaruru", "Te Puke"},{"Hamilton", "Tatuanui", "Putaruru"},{"Tauranga", "Edgecumbe", "Rotorua"},{"Cambridge", "Tauranga", "Rotorua", "Tokoroa"},
//				{"Putaruru", "Taumarunui", "Kuratau Junction", "Taupo"},{"Taumarunui", "Tokoroa", "Turangi"},{"Te Puke", "Whakatane"},{"Te Kaha", "Te Araroa"},{"Opotiki", "Waihau Bay"},
//				{"Edgecumbe", "Rotorua", "Opotiki"},{"Otorohanga", "Putaruru", "Te Puke", "Whakatane", "Murupara", "Taupo"},{"Rotorua", "Tuai"},{"Murupara", "Wairoa"},
//				{"Whakatane", "Te Kaha", "Matawai"},{"Opotiki", "Gisborne"},{"Waihau Bay", "Ruatoria"},{"Te Araroa", "Tokomaru Bay"},{"Ruatoria", "Tolaga Bay"},{"Tokomaru Bay", "Gisborne"},
//				{"Ohura", "Te Kuiti", "Tokoroa", "Kuratau Junction", "National Park"},{"Taumarunui", "Stratford"},{"Tokoroa", "Rotorua", "Turangi", "Rangitaiki"},
//				{"Okato", "Waitara", "Stratford"},{"New Plymouth", "Opunake"},{"Okato", "Hawera"},{"Opunake", "Stratford", "Patea"},{"Blenheim", "Wellington"},
//				{"Taupo", "Napier"},{"Matawai", "Tolaga Bay", "Wairoa"},{"Tutira", "Tuai", "Gisborne"},{"Napier", "Wairoa"},{"Raetihi", "Taumarunui", "Turangi"},
//				{"New Plymouth", "Hawera", "Ohura"},{"Whanganui", "Waiouru", "National Park"},{"Hunterville", "Raetihi", "Turangi"},{"Waiouru", "National Park", "Kuratau Junction", "Taupo"},
//				{"Hastings", "Dannevirke"},{"Hawera", "Whanganui"},{"Patea", "Raetihi", "Bulls"},{"Whanganui", "Hunterville", "Palmerston North", "Foxton"},{"Bulls", "Levin"},
//				{"Foxton", "Otaki", "Palmerston North"},{"Levin", "Paraparaumu"},{"Otaki", "Porirua"},{"Tutira", "Rangitaiki", "Hastings"},{"Napier", "Tikokino", "Waipawa"},
//				{"Hastings", "Waipukurau"},{"Dannevirke", "Waipawa"},{"Waiouru", "Bulls", "Feilding"},{"Hunterville", "Palmerston North"},{"Paraparaumu", "Wellington"},
//				{"Lower Hutt", "Featherston"},{"Wellington", "Upper Hutt"},{"Waipukurau", "Tikokino", "Woodville"},{"Palmerston North", "Dannevirke", "Eketahuna"},
//				{"Bulls", "Levin", "Feilding", "Woodville"},{"Masterton", "Woodville"},{"Featherston", "Eketahuna"},{"Upper Hutt", "Masterton", "Martinborough"},
//				{"Featherston"},{"Karamea", "Murchison", "Greymouth"},{"Westport"},{"Nelson", "Murchison"},{"Westport", "Wakefield", "St Arnaud", "Reefton", "Springs Junction"},
//				{"Murchison", "Springs Junction", "Greymouth"},{"Reefton", "Murchison", "Hanmer Springs", "Amberley"},{"Takaka", "Nelson"},{"Motueka", "Wakefield", "Blenheim"},
//				{"Porirua", "Lower Hutt", "Picton"},{"Picton", "Nelson", "St Arnaud", "Seddon"},{"Blenheim", "Ward"},{"Seddon", "Kaikoura"},{"Cheviot", "Ward"},
//				{"Westport", "Reefton", "Kumara Junction"},{"Springs Junction", "Amberley"},{"Blenheim", "Murchison"},{"Kaikoura", "Greta Valley"},{"Wanaka", "Fox Glacier"},
//				{"Cromwell", "Haast"},{"Franz Josef", "Kumara Junction"},{"Fox Glacier", "Hokitika"},{"Haast", "Franz Josef"},{"Twizel"},{"Twizel", "Fairlie"},
//				{"Lake Tekapo", "Geraldine", "Timaru"},{"Mount Cook", "Omarama", "Lake Tekapo"},{"Greymouth", "Hokitika", "Arthur's Pass"},{"Kumara Junction", "Springfield"},
//				{"Arthur's Pass", "Darfield"},{"Springfield", "Methven", "Christchurch"},{"Darfield", "Ashburton"},{"Ashburton", "Christchurch"},{"Amberley", "Cheviot"},
//				{"Hanmer Springs", "Greta Valley", "Woodend"},{"Christchurch", "Amberley"},{"Akaroa", "Rolleston", "Darfield", "Woodend"},{"Christchurch"},{"Ashburton", "Fairlie"},
//				{"Methven", "Rolleston", "Geraldine", "Temuka"},{"Twizel", "Kurow", "Cromwell"},{"Omarama", "Waimate", "Oamaru"},{"Alexandra", "Palmerston", "Mosgiel"},
//				{"Ashburton", "Timaru"},{"Fairlie", "Temuka", "Waimate"},{"Timaru", "Kurow", "Oamaru"},{"Palmerston", "Kurow", "Waimate"},{"Cromwell", "Kingston"},{"Queenstown", "Lumsden"},
//				{"Milford Sound", "Lumsden"},{"Winton", "Riverton"},{"Te Anau", "Kingston", "Gore", "Winton"},{"Wanaka", "Queenstown", "Alexandra", "Omarama"},
//				{"Cromwell", "Ranfurly", "Milton", "Gore"},{"Oamaru", "Ranfurly", "Dunedin"},{"Mosgiel", "Palmerston"},{"Ranfurly", "Dunedin", "Milton"},
//				{"Winton", "Lumsden", "Edendale", "Balclutha", "Alexandra"},{"Balclutha", "Mosgiel", "Alexandra"},{"Clifden", "Lumsden", "Gore", "Invercargill"},
//				{"Milton", "Gore"},{"Invercargill", "Gore"},{"Bluff", "Clifden"},{"Bluff", "Riverton", "Winton", "Edendale"},{"Oban", "Invercargill"},{"Bluff"}};
//
//		double[] latCoords = {-44.67184,-34.4266361,-40.68202,-40.990117,-36.68781,-34.992899,-35.1127598,-35.2297666,-35.2818684,-35.4069776,
//				-35.7283469,-35.5116663,-35.9382609,-36.2949669,-36.1994214,-36.1368675,-36.3840986,-36.6799207,-36.8534665,-36.3984914,
//				-36.584896,-36.7571639,-36.7930971,-37.1365279,-37.0673534,-37.202369,-37.5606311,-37.7876214,-37.8003499,-38.1823014,
//				-38.0651053,-38.3341925,-38.4675898,-38.4161765,-39.0028236,-36.8331861,-37.209164,-37.3925403,-37.3747613,-37.6223299,-37.6859006,
//				-37.8917889,-37.7853294,-38.0512585,-38.2168677,-38.878222,-37.9772022,-37.6141164,-37.7369518,-37.9509579,-38.1381493,-38.4563728,
//				-38.8127048,-38.0055135,-38.3574624,-37.6330949,-37.8919955,-38.1275279,-38.3744789,-38.8813562,-39.0394622,-38.6874922,-39.0579941,
//				-39.1895995,-39.4546624,-42.196574,-41.290916,-38.0944186,-38.661326,-39.03939,-39.2226486,-39.1727294,-39.3388499,-39.4264953,-39.4772125,
//				-38.9897822,-39.8160446,-39.7586904,-39.9347494,-40.1748706,-40.4730011,-40.6218161,-40.8060399,-40.9144636,-39.4908578,-39.6430115,
//				-39.8919781,-39.9910422,-39.9376536,-40.2259824,-41.1354918,-41.1240674,-41.2125751,-40.2073897,-40.336545,-40.356317,-40.6466355,-40.9495524,
//				-41.114939,-41.2183252,-41.754183,-41.246802,-41.4056242,-41.7995251,-42.1159384,-42.3347563,-41.112493,-41.09940685,-41.2887467,
//				-41.5155626,-41.6711969,-41.8264213,-42.4003723,-42.4499469,-42.5217947,-41.8023717,-42.8116534,-43.8800952,-44.6971416,-42.7178871,
//				-43.3889668,-43.4641803,-43.5950017,-44,-44.097582,-44.257483,-42.5848862,-42.9409528,-43.3366017,-43.4882301,-43.6324332,-43.5966504,
//				-42.9640263,-43.1512956,-43.3255,-43.530955,-43.82493765,-44.0921039,-43.9024396,-44.4877306,-44.7338879,-45.1295252,-44.2439593,
//				-44.3930254,-44.7338811,-45.0997681,-45.0317203,-45.3343708,-45.41449,-46.0352433,-45.7385616,-45.0379231,-45.2535683,-45.4842441,
//				-45.8739282,-45.8749828,-46.0984482,-46.1167784,-46.141439,-46.24078,-46.31452,-46.3509977,-46.4121229,-46.6014038,-46.8979908 };
//
//		double [] longCoords = {167.9254592,172.6774565,172.6844307,172.8176936,175.60374,173.5323214,173.2677315,173.9529996,174.0896775,
//				173.8048386,174.3206488,173.3917291,173.8630341,174.5229842,175.4172866,174.0252849,174.5141024,174.4505396,174.7655514,
//				174.6642316,174.6925888,175.5007016,175.088401,175.5456098,174.9470703,174.9057279,175.1599758,175.2813186,174.8744331,175.2155142,
//				174.818273,175.1657117,175.0145357,175.1111651,174.2364324,175.7024856,175.8709805,175.8404879,175.6750558,175.5952063,176.167505,
//				175.4691069,176.3270238,175.7799503,175.8705389,175.670222,176.8280177,177.9060602,177.6746511,176.9950483,176.252922,176.7060277,
//				177.144806,177.288287,177.5328987,178.3610171,178.3187514,178.3160945,178.2990965,175.2662649,175.0576703,176.0754385,174.0806474,
//				173.8803349,173.8601616,171.4640536,174.006908,176.8168188,178.0206487,177.4271389,176.8933343,175.4037274,174.2875945,175.2828804,
//				175.6697752,175.8069274,176.4530575,174.4848943,175.0539001,175.3856554,175.2822068,175.2865854,175.1880854,175.0061963,176.9179114,
//				176.8447684,176.4754283,176.5605821,175.567714,175.5645496,174.8396887,175.0699589,174.9057626,176.0982442,175.8674348,175.6112388,
//				175.7033205,175.6594413,175.32524,175.4594856,171.6035684,172.113264,173.0428332,172.3286812,171.8634561,172.1817907,173.0094875,
//				173.4288755,174.7772092,173.9602692,174.0733696,174.1379945,173.6803614,171.2079875,172.8302995,172.8450406,173.2735027,169.0436257,169.1364261,
//				170.9646469,170.1819048,170.017408,170.1421677,170.483333,170.8289409,170.0994145,171.1296106,171.5628387,171.9278207,172.1087152,
//				171.6472608,172.3850047,172.9682943,172.7329869,172.6659,172.6366455,173.0209638,171.2425898,171.7503327,169.9688906,170.4694561,170.099781,
//				171.2797349,171.2509786,171.042339,170.9684232,168.6608096,168.7175147,167.717489,167.7162069,168.4424386,169.1905128,169.3870836,
//				170.715319,170.503488,170.347348,168.9447811,169.9624539,168.3239863,169.736157,168.78518,168.0202689,168.3483269,168.3398682,168.1283778};
//
//		double [] imageX = {568,1584,1531,1574,1661,1782,1729,1845,1846,1797,1943,1766,1817,1946,2160,1863,1944,1945,2015,2011,2013,2223,2127,2191,2068,
//				2126,2124,2125,2029,2107,2007,2119,2070,2023,1977,2252,2252,2254,2218,2196,2352,2193,2397,2251,2256,2194,2457,2737,2680,2508,2398,2464,
//				2534,2590,2119,2809,2820,2821,2821,2130,2062,2308,1871,1824,1824,1870,1823,2386,2721,2582,2511,2130,1941,2125,2243,2249,2393,2009,2062,
//				2143,2124,2125,2109,2063,2472,2472,2459,2418,2197,2197,2018,2088,2040,2359,2313,2198,2272,2203,2139,2170,1320,1425,1589,1497,1373,1440,
//				1607,1654,1998,1821,1824,1824,1760,1232,1566,1651,1692,874,872,1182,1088,968,1012,1100,1184,1012,1230,1326,1362,1434,1394,1440,1620,1536,
//				1536,1538,1610,1246,1364,1008,114,1074,1318,1254,1224,1226,732,758,566,566,702,838,890,1132,1132,1076,796,1002,692,942,764,582,692,692,646};
//
//		double [] imageY = {3138,166,1979,2020,243,312,316,378,428,475,497,522,588,639,652,636,719,801,880,704,768,825,832,934,929,987,1052,1131,1147,1213,
//				1243,1294,1345,1390,1438,854,918,1001,985,1061,1098,1149,1149,1207,1251,1437,1174,1122,1174,1127,1270,1335,1405,1225,1294,1121,1179,1248,1323,
//				1437,1506,1419,1490,1532,1593,1632,2128,1491,1396,1505,1536,1494,1564,1593,1646,1490,1714,1671,1724,1806,1871,1927,1985,2032,1609,1656,1702,1745,
//				1756,1807,2076,2086,2097,1807,1856,1856,1950,2019,2086,2117,2294,2143,2206,2299,2383,2446,2089,2140,2129,2206,2297,2375,2438,2480,2514,2304,2508,
//				2928,3074,2570,2664,2784,2866,2960,2962,3032,2524,2618,2718,2792,2834,2850,2582,2660,2728,2792,2866,2960,2920,3082,3180,3262,2978,3036,3138,3224,
//				3242,3326,3358,3454,3446,3242,3292,3320,3474,3474,3538,3552,3576,3578,3614,3630,3642,3692,3782};
//
//		City[] cityList = new City[cityNames.length];
//		Hashtable<String, Link> linkList = new Hashtable<String, Link>();
//
//		for(int i = 0; i < cityNames.length; i++) {
//			City city = new City();
//			city.setName(cityNames[i]);
//			city.setCoordinates(new Point2D.Double(longCoords[i], latCoords[i]));
//			city.setPointOnPicture(new Point2D.Double(imageX[i],imageY[i]));
//			cityList[i] = city;
//			cities.put(cityNames[i], city);
//		}
//
//		for(int i = 0; i < linkedNames.length; i++) {
//			City city = cityList[i];
//			ArrayList<Link> temp = new ArrayList<Link>();
//
//			for(int j = 0; j < linkedNames[i].length; j++) {
//				City city2 = cities.get(linkedNames[i][j]);
//				if(linkList.get(city2.name + city.name) == null) {
//					Link link = new Link();
//					link.setDist(city.coordinates.distance(city2.coordinates) * degreesToKmConversion + 1);
//					link.setTime(city.coordinates.distance(city2.coordinates) * degreesToKmConversion + 1);
//					link.setCity1(city);
//					link.setCity2(city2);
//					link.setName(city.name + city2.name);
//					linkList.put(city.name + city2.name, link);
//					temp.add(link);
//				} else {
//					temp.add(linkList.get(city2.name + city.name));
//				}
//			}
//
//			city.setLinks(temp);
//		}
//
//		try {
//			write(cities, "Cities.xml");
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		try {
			cities = read("Cities.xml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		route = new ArrayList<City>();
		attractions = new ArrayList<Attraction>();
	}


	public void findShortestPath(String orig, String dest){
		origin = cities.get(orig);
		destination = cities.get(dest);

		Comparator<City> comparator = new CostComparator();
		PriorityQueue<City> queue = new PriorityQueue<City>(11, comparator);

		queue.add(origin);
		City currentCity = origin;
		while(!currentCity.name.equals(destination.name) && !queue.isEmpty()) {
			currentCity = queue.poll();
			for(Link link : currentCity.links) {
				City city;
				if(link.city1.name.equals(currentCity.name)) city = link.city2;
				else city = link.city1;
				if(city.distanceToReach == 0 || city.distanceToReach > currentCity.distanceToReach + link.dist) {
					city.distanceToReach = currentCity.distanceToReach + link.dist;
					city.estimatedCost = city.distanceToReach + (city.coordinates.distance(destination.coordinates) * degreesToKmConversion);
					if(!queue.contains(city)) queue.offer(city);
				}
			}
		}

		origin.distanceToReach = 0;
		route.add(destination);
		while(route.get(route.size() - 1).name != origin.name) {
			currentCity = route.get(route.size() - 1);
			for(Link link : currentCity.links) {
				if(link.city1.name.equals(currentCity.name)) {
					if(link.dist + link.city2.distanceToReach == currentCity.distanceToReach) {
						route.add(link.city2);
						for(Attraction attraction : link.attractions) attractions.add(attraction);
						break;
					}
				} else {
					if(link.dist + link.city1.distanceToReach == currentCity.distanceToReach) {
						route.add(link.city1);
						for(Attraction attraction : link.attractions) attractions.add(attraction);
						break;
					}
				}
			}
		}
		cost = destination.distanceToReach;
	}

	public class CostComparator implements Comparator<City> {
		public int compare(City city1, City city2) {
			if(city1.estimatedCost == city2.estimatedCost) return 0;
			return (city1.estimatedCost < city2.estimatedCost) ? -1 : 1;
		}

	}

	public static void write(Hashtable<String, City> city, String filename) throws Exception{
		XMLEncoder encoder =
				new XMLEncoder(
						new BufferedOutputStream(
								new FileOutputStream(filename)));
		encoder.writeObject(city);
		encoder.close();
	}

	public static Hashtable<String, City> read(String filename) throws Exception {
		XMLDecoder decoder =
				new XMLDecoder(
						new BufferedInputStream(
								new FileInputStream(filename)));
		Hashtable<String, City> l = (Hashtable<String, City>) decoder.readObject();
		decoder.close();
		return l;
	}

	public ArrayList<City> getRoute(){ return route; }
	public ArrayList<Attraction> getAttractions(){ return attractions; }
	public double getCost() { return cost; }
}