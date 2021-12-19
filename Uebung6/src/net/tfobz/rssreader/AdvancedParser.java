package net.tfobz.rssreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class AdvancedParser {
	private Channel c = new Channel();
	private Item i = new Item();
	private List<Item> items;
	private boolean isItem = false;
	
	// Gibt den Channel zurück, dieser wird aber automatisch vom Parser gesetzt
	public Channel getChannel() {
		return c;
	}

	// Gibt eine Liste von Items zurück. Sollte die gleiche Liste wie Channel.getItems() sein.
	public List<Item> getItems() {
		return items;
	}

	public void parser(String urlstring) throws IOException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream in;
		// Ändert Inputstream ob es ein File oder URL ist
		if (new File(urlstring).exists()) {
			in = new FileInputStream(new File(urlstring));
		} else {
			URL url = new URL(urlstring);
			in = url.openStream();
		}
		
		XMLStreamReader parser = factory.createXMLStreamReader(in);
		// Fast alles von SimpleParser übernommen
		String characters = "";
		while (parser.hasNext()) {
			int elementType = parser.next();
			switch (elementType) {
				case XMLStreamConstants.START_DOCUMENT: {
					System.out.println(elementType + " START_DOCUMENT: " + parser.getVersion());
					break;
				}
				case XMLStreamConstants.END_DOCUMENT : {
					System.out.println(elementType + " END_DOCUMENT: ");
					parser.close();
					break;
				}
				case XMLStreamConstants.NAMESPACE: {
					System.out.println(elementType + "NAMESPACE: " + parser.getNamespaceURI());
					break;
				}
				case XMLStreamConstants.START_ELEMENT: {
					characters = "";
					System.out.print(elementType + " START_ELEMENT: " + parser.getLocalName() + " ");
					// Wenn ein start-element ein Item ist, wird die Item funktion aktiviert
					// Somit gelangen alle Attribute in ein Item
					if (parser.getLocalName().equals("item"))
						isItem = true;
					
					for (int i = 0; i < parser.getAttributeCount(); i++)
						System.out.print(parser.getAttributeName(i) + "=" + parser.getAttributeValue(i) + " ");
					System.out.println();
					break;
				}
				case XMLStreamConstants.END_ELEMENT: {
					String content = characters.replaceAll("\n", "").trim();
					System.out.println(elementType + " END_ELEMENT: " + parser.getLocalName() + " " + content);
					// Wenn hingegen das Ende ein Item ist, soll die Item Funktion beendet werden und ein neues Item angelegt werden
					if (parser.getLocalName().equals("item")) {
						// Deaktiviert Item Funktion
						isItem = false;
						System.out.println("Item finished: " + i.toString());
						// Fügt Item dem Channel hinzu
						items = c.getItems();
						items.add(i);
						c.setItems(items);
						// Erstellt ein neues leeres Item
						i = new Item();
					}
					// Gibt Attribute dem Parser über, der sie dann in Channels oder Items einfügt
					String parseattr = parser.getLocalName() + " " + content;
					attributeAdder(parseattr);
					characters = "";
					break;
				}
				case XMLStreamConstants.CHARACTERS: {
					if (!parser.isWhiteSpace() && parser.getText() != null && parser.getText().length() > 0)
						characters+=parser.getText();
					break;
				}
			}
		}
	}
	
	private void attributeAdder(String attributes) {
		String[] array = attributes.split(" ");
		
		if (isItem) {
			switch(array[0]) {
			case "title":
				i.setTitle(attributes.substring(attributes.indexOf(" ")));
				break;
			case "link":
				i.setLink(attributes.substring(attributes.indexOf(" ")));
				break;
			case "description":
				i.setDescription(attributes.substring(attributes.indexOf(" ")));
				break;
			case "author":
				i.setAuthor(attributes.substring(attributes.indexOf(" ")));
				break;
			case "pubDate":
				i.setPubDate(attributes.substring(attributes.indexOf(" ")));
				break;
			}
		} else {
			switch(array[0]) {
			case "title":
				c.setTitle(attributes.substring(attributes.indexOf(" ")));
				break;
			case "link":
				c.setLink(attributes.substring(attributes.indexOf(" ")));
				break;
			case "description":
				c.setDescription(attributes.substring(attributes.indexOf(" ")));
				break;
			case "language":
				c.setLanguage(attributes.substring(attributes.indexOf(" ")));
				break;
			case "copyright":
				c.setCopyright(attributes.substring(attributes.indexOf(" ")));
				break;
			}
		}
	}

	public static void main(String[] args) {
		AdvancedParser ap = new AdvancedParser();
		try {
			ap.parser("https://www.provinz.bz.it/wetter/rss.asp");
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

}
