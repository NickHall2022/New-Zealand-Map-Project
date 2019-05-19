import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class Main {

	public static class myActionListener implements ActionListener {
		private JComboBox<String> origin;
		private JComboBox<String> destination;
		private Graph graph;
		private MapPanel panel;
		private JLabel text;
		public myActionListener(JComboBox<String> origin , JComboBox<String> destination, Graph graph, MapPanel panel, JLabel text){
			this.origin = origin;
			this.destination = destination;
			this.graph = graph;
			this.panel = panel;
			this.text = text;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (origin.getSelectedIndex() != -1 && destination.getSelectedIndex() != -1) {
				graph.findShortestPath(origin.getSelectedItem().toString(), destination.getSelectedItem().toString());
				ArrayList <City> route = graph.getRoute();
				ArrayList <Attraction> attractions = graph.getAttractions();
				try {
					panel.map = ImageIO.read(new File("NewZealandMapDeSat.jpg"));
				}
				catch (IOException e) { System.out.println(e.getMessage()); }
				BufferedImage image = panel.map;
				for(int i = 1; i < route.size(); i++){
					Graphics2D g2 = image.createGraphics();
					g2.setColor(Color.green);
					g2.setStroke(new BasicStroke(12));
					City city1 = route.get(i-1);
					City city2 = route.get(i);
					g2.draw(new Line2D.Double(city1.getPointOnPicture().getX(), city1.getPointOnPicture().getY(), city2.getPointOnPicture().getX(), city2.getPointOnPicture().getY()));
				}
				String output = "<html><b>ROUTE:</b><br>";
				for(int x = route.size() - 1; x > -1; x--) {
					output = output + route.get(x).name + "<br>";
				}
				output = output + "<br>ATTRACTIONS:<br>";
				for(int x = attractions.size() - 1; x > -1; x--) {
					output += attractions.get(x).name + "<br>";
				}
				output += "<br>COST: " + graph.getCost();
				output += "</html>";
				text.setText(output);
				System.out.println("Cost: " + graph.getCost());
			}
		}
	}

	public static void main (String[] args) {
		JFrame frame = new JFrame();
		int frameWidth = 1000;
		int frameHeight = 990;

		frame.setSize(frameWidth, frameHeight);
		frame.setTitle("New Zealand");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("NewZealandMap.jpg"));
		}
		catch (IOException e) { System.out.println(e.getMessage()); }
		MapPanel mapPanel = new MapPanel(image);
		frame.add(mapPanel, BorderLayout.CENTER);

		String [] cityNames = {"Milford Sound" ,"Cape Reinga" ,"Collingwood" ,"Takaka","Pukenui","Mangonui","Kaitaia" ,"Kerikeri" ,
				"Paihia" ,"Kaikohe" ,"Whangarei" ,"Opononi" ,"Dargaville" ,"Wellsford" ,"Great Barrier Is." ,"Ruawai" ,"Tauhoa" ,
				"Helensville" ,"Auckland" ,"Warkworth" ,"Orewa" ,"Coromandel" ,"Waiheke Is." ,"Thames" ,"Papakura" ,"Pukekohe" ,
				"Huntly" ,"Hamilton" ,"Raglan" ,"Otorohanga" ,"Kawhia" ,"Te Kuiti" ,"Piopio" ,"Mokau" ,"Waitara" ,"Whitianga" ,"Whangamata" ,
				"Waihi" ,"Paeroa" ,"Tatuanui" ,"Tauranga" ,"Cambridge" ,"Te Puke" ,"Putaruru" ,"Tokoroa" ,"Kuratau Junction","Edgecumbe" ,
				"Waihau Bay" ,"Te Kaha" ,"Whakatane" ,"Rotorua" ,"Murupara" ,"Tuai" ,"Opotiki" ,"Matawai" ,"Te Araroa" ,"Ruatoria" ,"Tokomaru Bay",
				"Tolaga Bay" ,"Taumarunui" ,"Ohura" ,"Taupo" ,"New Plymouth" ,"Okato","Opunake" ,"Hawera" ,"Picton" ,"Rangitaiki" ,"Gisborne",
				"Wairoa" ,"Tutira" ,"National Park" ,"Stratford" ,"Raetihi" ,"Waiouru" ,"Turangi" ,"Tikokino" ,"Patea" ,"Whanganui" ,"Bulls",
				"Foxton" ,"Levin" ,"Otaki" ,"Paraparaumu" ,"Napier" ,"Hastings" ,"Waipawa" ,"Waipukurau" ,"Hunterville" ,"Feilding" ,"Porirua",
				"Upper Hutt" ,"Lower Hutt" ,"Dannevirke" ,"Woodville" ,"Palmerston North" ,"Eketahuna" ,"Masterton" ,"Featherston" ,
				"Martinborough", "Westport" ,"Karamea" ,"Wakefield" ,"Murchison" ,"Reefton" ,"Springs Junction" ,"Motueka" ,"Nelson",
				"Wellington" ,"Blenheim" ,"Seddon" ,"Ward" ,"Kaikoura" ,"Greymouth" ,"Hanmer Springs" ,"St Arnaud" ,"Cheviot",
				"Haast" ,"Wanaka" ,"Hokitika" ,"Franz Josef" ,"Fox Glacier" ,"Mount Cook" ,"Lake Tekapo","Fairlie" ,"Twizel" ,"Kumara Junction",
				"Arthur's Pass" ,"Springfield" ,"Darfield" ,"Methven" ,"Rolleston" ,"Greta Valley" ,"Amberley" ,"Woodend" ,"Christchurch" ,
				"Akaroa" ,"Geraldine" ,"Ashburton" ,"Omarama" ,"Kurow" ,"Ranfurly" ,"Temuka" ,"Timaru" ,"Waimate" ,"Oamaru" ,"Queenstown" ,
				"Kingston" ,"Te Anau" ,"Clifden","Lumsden" ,"Cromwell" ,"Alexandra" ,"Palmerston" ,"Dunedin" ,"Mosgiel" ,"Gore",
				"Milton" ,"Winton" ,"Balclutha" ,"Edendale" ,"Riverton" ,"Invercargill" ,"Bluff" ,"Oban"};

		JComboBox<String> origin = new JComboBox<String>(cityNames);
		origin.setSelectedIndex(-1);
		origin.setName("Starting Location");
		JComboBox<String> destination = new JComboBox<String>(cityNames);
		destination.setSelectedIndex(-1);
		destination.setName("Destination");

		JButton routeButton = new JButton("Find Route");

		JPanel boxPanel = new JPanel();
		boxPanel.add(origin, BorderLayout.WEST);
		boxPanel.add(destination, BorderLayout.CENTER);
		boxPanel.add(routeButton, BorderLayout.EAST);
		frame.add(boxPanel, BorderLayout.NORTH);
		JLabel textOutput = new JLabel("Please select a route to get path information.");
		frame.add(textOutput, BorderLayout.EAST);

		frame.setVisible(true);

		Graph graph = new Graph();
		ActionListener myListener = new myActionListener(origin,destination,graph,mapPanel,textOutput);
		routeButton.addActionListener(myListener);

//		routeButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if (origin.getSelectedIndex() != -1 && destination.getSelectedIndex() != -1) {
//					graph.findShortestPath(origin.getSelectedItem().toString(), destination.getSelectedItem().toString());
//					ArrayList <City> route = graph.getRoute();
//					ArrayList <Attraction> attractions = graph.getAttractions();
//					for(int i = 1; i < route.size(); i++){
//						Graphics2D g2d = image.createGraphics();
//					}
//					System.out.println("Route:");
//					for(int x = route.size() - 1; x > -1; x--) {
//						System.out.println(route.get(x).name);
//					}
//					System.out.println("Attractions:");
//					for(int x = attractions.size() - 1; x > -1; x--) {
//						System.out.println(attractions.get(x).name);
//					}
//					System.out.println("Cost: " + graph.getCost());
//				}
//			}
//
//		});
	}
}