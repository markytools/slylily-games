package com.connectcoins.store;

public class ItemPrice {
	private float priceInCC = 0;
	private float priceInDollar = 0;
	private String description;
	
	public ItemPrice(float priceInCC, float priceInDollar,
			String description) {
		super();
		this.setPriceInCC(priceInCC);
		this.setPriceInDollar(priceInDollar);
		this.setDescription(description);
	}

	public float getPriceInCC() {
		return priceInCC;
	}

	public void setPriceInCC(float priceInCC) {
		this.priceInCC = priceInCC;
	}

	public float getPriceInDollar() {
		return priceInDollar;
	}

	public void setPriceInDollar(float priceInDollar) {
		this.priceInDollar = priceInDollar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
