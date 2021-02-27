package com.restfulwebproject.demo.Controllers;

import com.restfulwebproject.demo.Servicelayer.ServiceInvoice;
import com.restfulwebproject.demo.repository.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Invoices")
public class InvoiceController {

    private final ServiceInvoice service;

    @Autowired
    public InvoiceController(ServiceInvoice service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public Iterable<Invoice> getAllInvoices()
    {
       return  service.findALlInvoices();
    }


}
