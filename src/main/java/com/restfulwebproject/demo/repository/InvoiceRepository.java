package com.restfulwebproject.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

@Repository
public class InvoiceRepository implements  IInvoiceRepository {

    private static final String FIND_ALL_SQL = "select * from invoices";
    private static final String FIND_BY_MONTH_SQL = "select * from invoices where date_part('month', date) = ?";
    private static final String COUNT_SQL = "select count(*) from invoices";
    private static final String FIND_BY_ID_SQL = "select * from invoices where id = ?";
    private static final String FIND_BY_DATE_SQL = "select * from invoices where date = ?";
    private static final String SAVE_SQL = "insert into invoices (name, address, date, total) values (:name, :address, :date, :total)";



    private static final Vector<Invoice> ms_invoices =new Vector();
   private static int m_currentId;


    private final JdbcTemplate m_jdbcTemplate;

    public InvoiceRepository(JdbcTemplate m_jdbcTemplate) {
        this.m_jdbcTemplate = m_jdbcTemplate;
    }

    private void fillInvoices(ResultSet resultSet, List<Invoice> invoices) throws SQLException
    {
        do {
            var invoiceId =  resultSet.getInt(1); //resultSet.getInt("invoice_id");
            var name = resultSet.getString(2);
            var address = resultSet.getString(3);
            var date = resultSet.getDate(4).toLocalDate();
            var total = resultSet.getDouble(5);

            invoices.add(new Invoice(invoiceId, name, address, date, BigDecimal.valueOf(total)));
        } while (resultSet.next());
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

       ms_invoices.remove(0);

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
    public Iterable<Invoice> findAll()
    {
        var invoices = new ArrayList<Invoice>();


        m_jdbcTemplate.query(FIND_ALL_SQL, ( ResultSet rs) -> fillInvoices(rs, invoices));

        return invoices;
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
