package bookshop.dao;

import bookshop.entities.Genres;

import java.util.List;

public interface GenresDAO {
    public List<Genres> loadAllGenres();
    public Genres getGenreById(Integer id);
    public boolean addNewGenre(Genres genres);
    public boolean deleteGenre(Integer id);
    public boolean updateGenre(Genres genres);
}
