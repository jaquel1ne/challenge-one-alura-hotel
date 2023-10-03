package controllers;

import java.util.List;

import model.dao.ReservaDAO;
import model.entities.Reserva;

public class ReservaController {

    private ReservaDAO reservaDAO;

    public ReservaController(ReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    public Long salvar(Reserva reserva) {
        return reservaDAO.insert(reserva);
    }

    public List<Reserva> find() {
        return reservaDAO.find(null);
    }
    
    public Reserva buscarPorId(Long id) {
        return reservaDAO.findById(id);
    }

    public void update(Reserva reserva) {
        reservaDAO.update(reserva);
    }

    public void delete(Reserva reserva) {
        reservaDAO.delete(reserva);
    }
}
