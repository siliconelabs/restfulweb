package com.restfulwebproject.demo.Servicelayer;

import com.restfulwebproject.demo.repository.IInvoiceRepository;
import com.restfulwebproject.demo.repository.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ServiceInvoice {

    private final IInvoiceRepository m_invoiceRepository;

    @Autowired
    public ServiceInvoice(IInvoiceRepository m_invoiceRepository) {
        this.m_invoiceRepository = m_invoiceRepository;
    }

    public Invoice saveInvoce(Invoice invoice)
    {
        try
        {
            return m_invoiceRepository.save(invoice);
        }
        catch(Throwable ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Iterable<Invoice> findALlInvoices()
    {
        return m_invoiceRepository.findAll();
    }

    public Iterable<Invoice> findByMonth(int mon)
    {
        return m_invoiceRepository.findInvoicesByMonth(mon);
    }

    public  Iterable<Invoice> findByDate(LocalDate date)
    {
        return m_invoiceRepository.findInvoicesByDate(date);
    }


    public boolean deleteInvoice(int id) {
        Optional<Invoice> invoiceDb = m_invoiceRepository.findById(id);

        if(invoiceDb.isPresent()) {
            m_invoiceRepository.delete(invoiceDb.get());
            return true;
        }else {
            return false;
            //throw new RuntimeException("not found");
        }

    }

    public Invoice  updateInvoice(Invoice invoice)
    {

        Optional<Invoice> invoiceDb = m_invoiceRepository.findById(invoice.getId());

        if(invoiceDb.isPresent()) {
            Invoice productUpdate = invoiceDb.get();
            productUpdate.setId(invoice.getId());
            productUpdate.setName(invoice.getName());

            m_invoiceRepository.save(productUpdate);
            return productUpdate;
        }else {

        }

        return   m_invoiceRepository.save(invoice);
    }

}
