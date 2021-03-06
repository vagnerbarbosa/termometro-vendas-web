package com.vagnerbarbosa.termometro.vendas.web.managedbeans;

import com.vagnerbarbosa.termometro.vendas.web.model.Sales;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.List;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class SalesServiceManagedBean implements Serializable {

    private ObjectMapper mapper = new ObjectMapper();
    private List<Sales> sales = null;
    private Date initialDate = new Date(System.currentTimeMillis());
    private Date finalDate = new Date(System.currentTimeMillis());

    public SalesServiceManagedBean() {
    }

    public List<Sales> getSales() throws IOException {
        if (this.sales == null) {
            Client c = Client.create();
            WebResource wr = c.resource("http://192.168.19.250:8080/riodopeixe-rest/webservice/sales/");
            String json = wr.get(String.class);
            this.sales = (List<Sales>) mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Sales.class));
        }
        return sales;
    }

    public List<Sales> getSalesByDate(Date initialDate, Date finalDate) throws IOException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dat1 = df.format(initialDate);
        String dat2 = df.format(finalDate);
        Client c = Client.create();
        WebResource wr = c.resource("http://192.168.19.250:8080/riodopeixe-rest/webservice/sales/" + dat1 + "/" + dat2);
        String json = wr.get(String.class);
        this.sales = (List<Sales>) mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Sales.class));
        return this.sales;  
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }    

}
