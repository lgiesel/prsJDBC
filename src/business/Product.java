package business;

public class Product {

    private int id;
    private int vendorID;
    private String partNumber;
    private String name;
    private double price;
    private String unit;
    private String photoPath;

	public Product() {
		id = 0;
		vendorID = 0;
		partNumber = "";
		name = "";
		price = 0.0;
		unit = "";
		photoPath = "";
	}
    	
	public Product(int id, int vendorID, String partNumber, String name, double price, String unit, String photoPath) {
		super();
		this.id = id;
		this.vendorID = vendorID;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
	}
	
	public Product (int id) {
		this.id = id;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVendorID() {
		return vendorID;
	}

	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotopath() {
		return photoPath;
	}

	public void setPhotopath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {//int id, int vendorID, String partNumber, String name, double price, String unit, String photoPath) {
		return "Product= [id=" + id + ", vendorID=" + vendorID + ", partNumber=" + partNumber + ", name=" + name + 
			           ", price=" + price + ", unit=" + unit + ", photoPath=" + photoPath + "]";
	}

}
