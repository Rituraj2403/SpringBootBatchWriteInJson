package com.springboot.batch.writeinjson.model;

public class ProductDTO {
    private String p_FirstName;
	private String p_LastName;
    private String p_CompanyName;
   
   public ProductDTO(){
	   
   }
  
    public ProductDTO(String p_FirstName, String p_LastName, String p_CompanyName) {
		super();
		this.p_FirstName = p_FirstName;
		this.p_LastName = p_LastName;
		this.p_CompanyName = p_CompanyName;
	}


	public String getP_FirstName() {
		return p_FirstName;
	}


	public void setP_FirstName(String p_FirstName) {
		this.p_FirstName = p_FirstName;
	}


	public String getP_LastName() {
		return p_LastName;
	}


	public void setP_LastName(String p_LastName) {
		this.p_LastName = p_LastName;
	}


	public String getP_CompanyName() {
		return p_CompanyName;
	}


	public void setP_CompanyName(String p_CompanyName) {
		this.p_CompanyName = p_CompanyName;
	}


	@Override
	public String toString() {
		return "ProductDTO [p_FirstName=" + p_FirstName + ", p_LastName=" + p_LastName + ", p_CompanyName="
				+ p_CompanyName + "]";
	}
    
}
