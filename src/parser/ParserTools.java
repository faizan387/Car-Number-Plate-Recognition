package parser;

public class ParserTools {
	
	public static int parseInt(String code){
		return Integer.parseInt(code);
	}
	
	public static float parseFloat(String code){
		return Float.parseFloat(code);
	}
	
	public static double parseDouble(String code){
		return Double.parseDouble(code);
	}

	public static int[] parseIntArray(String code) {
		code = code.substring(1, code.length()-1);
		String[] data = code.split(",");
		int[] d = new int[data.length];
		for(int i = 0; i <d.length; i++){
			d[i] = Integer.parseInt(data[i].trim());
		}
		return d;
	}

	public static double[] parseDoubleArray(String code) {
		code = code.substring(1, code.length()-1);
		String[] data = code.split(",");
		double[] d = new double[data.length];
		for(int i = 0; i <d.length; i++){
			d[i] = Double.parseDouble(data[i].trim());
		}
		return d;
	}
	
	public static float[] parseFloatArray(String code) {
		code = code.substring(1, code.length()-1);
		String[] data = code.split(",");
		float[] d = new float[data.length];
		for(int i = 0; i <d.length; i++){
			d[i] = Float.parseFloat(data[i].trim());
		}
		return d;
	}
	
	public static String createSpaces(int amount){
		String res = "";
		for(int i = 0; i<  amount; i++){
			res += " ";
		}
		return res;
	}

	
	
	
	
}
