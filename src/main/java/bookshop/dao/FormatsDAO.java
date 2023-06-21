package bookshop.dao;

import bookshop.entities.Formats;

import java.util.List;

public interface FormatsDAO {
    public List<Formats> loadALlFormats();
    public Formats getFormatById(Integer id);
    public boolean addNewFormat(Formats formats);
    public boolean deleteFormatById(Integer id);
    public boolean updateFormat(Formats formats);
}
