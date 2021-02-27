package com.restfulwebproject.demo.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

@Repository
public class InvoiceRepository implements  IInvoiceRepository {

   private static final Vector<Invoice> ms_invoices =new Vector();
   private static int m_currentId;

   static{
       ms_invoices.add(new Invoice(1,"siliconeLab","NJ",LocalDate.now(), BigDecimal.TEN));
       ms_invoices.add(new Invoice(2,"elektrik","NJ",LocalDate.of(2021,12,23), BigDecimal.TEN));
       ms_invoices.add(new Invoice(3,"gas","NJ",LocalDate.of(2020,3,21), BigDecimal.TEN));
       ms_invoices.add(new Invoice(4,"su","NJ",LocalDate.now(), BigDecimal.TEN));

   }


    @Override

    public Iterable<Invoice> findInvoicesByMonth(int month) {
        return ms_invoices.stream().filter(invoice -> invoice.getDate().getMonthValue()==month).collect(Collectors.toList());
    }

    @Override
    public Iterable<Invoice> findInvoicesByDate(LocalDate date) {
        return ms_invoices.stream().filter(invoice -> invoice.getDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return ms_invoices.size();
    }

    @Override
    public void delete(Invoice entity) {

       throw new UnsupportedOperationException();

    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void delete(Iterable<? extends Invoice> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existById(Integer integer) {
        return ms_invoices.stream().anyMatch(invoice -> invoice.getId()==integer);
    }

    @Override
    public Iterable<Invoice> findAll() {
        return ms_invoices;
    }

    @Override
    public Optional<Invoice> findById(Integer id) {
        return ms_invoices.stream().filter(invoice -> invoice.getId()==id).findFirst();
    }

    @Override
    public <E extends Invoice> E save(E invoiceTobeSave) {

         invoiceTobeSave.setId(++m_currentId);
         ms_invoices.add(invoiceTobeSave);
         return invoiceTobeSave;
    }

    @Override
    public <E extends Invoice> E saveAll(Iterable<E> entity)
    {
          throw new UnsupportedOperationException();
    }
}
