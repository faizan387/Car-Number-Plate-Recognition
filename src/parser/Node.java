package parser;

import java.util.ArrayList;


public class Node {
	
	private String name;
	private ArrayList<Node> childs = new ArrayList<>();
	private ArrayList<Attribute> attributes = new ArrayList<>();
	
	public Node(String name) {
		this.name = name;
	}
	
	public boolean addAttribute(Attribute att){
		if(att != null){
			if(this.containsAttribute(att)){
				return false;
			}
			attributes.add(att);
			return true;
		}
		return false;
	}
	
	public boolean addAttribute(String att, String value){
		return this.addAttribute(new Attribute(att,value));
	}
	
	public boolean addChild(String name){
		return this.addChild(new Node(name));
	}
	
	public boolean addChild(Node n){
		if(n != null){

			if(this.containsChild(n)){
				return false;
			}
			childs.add(n);
			return true;
		}
		return false;
	}
	
	public Node getChild(String child) {
		for(Node r:childs){
			if(r.getName().equals(child)){
				return r;
			}
		}
		return null;
	}
	
	public Attribute getAttribute(String key) {
		for(Attribute r:attributes){
			if(r.getName().equals(key)){
				return r;
			}
		}
		return null;
	}
	
	public void setAttribute(String att, String value){
		if(this.getAttribute(att) != null){
			this.getAttribute(att).setValue(value);
		}
	}
	
	public boolean removeAttribute(String att){
		return this.removeAttribute(new Attribute(att, ""));
	}
	
	public boolean removeChild(String child){
		return this.removeChild(new Node(child));
	}
	
	public boolean removeAttribute(Attribute att){
		int index = 0;
		for(Attribute r:attributes){
			if(r.equals(att)){
				childs.remove(index);
				return true;
			}index++;
		}
		return false;
	}
	
	public boolean removeChild(Node child) {
		int index = 0;
		for(Node r:childs){
			if(child.equals(r)){
				childs.remove(index);
				return true;
			}index++;
		}
		return false;
	}
	
	public boolean containsAttribute(Attribute s){
		for(Attribute r:attributes){
			if(r.equalAttribute(s)){
				return true;
			}
		}
		return false;
	}

	public boolean containsChild(Node s){
		for(Node r:childs){
			if(s.equals(r)){
				return true;
			}
		}
		return false;
	}
	
	public static Node parse(String c) throws Exception{
		String[] lines = c.split("[;>]");
		Node n = new Node(lines[0].substring(1, lines[0].length()));
	
		int i = 1;
		while(i < lines.length-1){
			String currentLine = lines[i].trim();
			
			String nodeName = "";
			if(currentLine.startsWith("<")){
				nodeName = currentLine.substring(1);
				String batch = "";
				while(!lines[i].trim().startsWith("</"+nodeName)){
					batch += lines[i].trim() + ";";
					i++;
				}
				batch += lines[i].trim() + ";";
				n.addChild(Node.parse(batch));
			}
			else{
				n.addAttribute(Attribute.parse(currentLine));
				
			}i++;
		}
		
		return n;
	}

	public String toParse(int spacesLeft) {
		String res = ParserTools.createSpaces(spacesLeft) + "<" + name + ">" + "\n";
		
		for(Attribute at:attributes){
			res += at.toParse(spacesLeft + 4) + "\n";
		}
		
		for(Node n:childs){
			res += n.toParse(spacesLeft + 4);
		}
		
		res += ParserTools.createSpaces(spacesLeft) + "</" + name + ">"+ "\n";
		return res;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Node> getChilds(){
		return this.childs;
	}
	
	public boolean equals(Object o){
		if(o.getClass() != this.getClass()){
			return false;
		} if(((Node)o).getName() != this.getName()){
			return false;
		}
		return true;
	}
}
