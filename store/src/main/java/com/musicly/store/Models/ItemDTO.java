package com.musicly.store.Models;

public class ItemDTO {
    public int Id;
    public int Type;
    public String Brand;
    public String Description;
    public Double Price;
    public int NoofItems;

    public ItemDTO(int Id, int Type,String Brand,String Description,Double Price, int NoofItems){
        this.Id= Id;
        this.Type= Type;
        this.Brand=Brand;
        this.Description=Description;
        this.Price = Price;
        this.NoofItems= NoofItems;
    }
    
}
