package controllers;

import java.util.List;

import model.dao.HospedeDAO;
import model.entities.Hospede;

public class HospedeController {
    
    private HospedeDAO hospedeDAO;

    public HospedeController(HospedeDAO hospedeDAO) {
        this.hospedeDAO = hospedeDAO;
    }

    public void salvar(Hospede hospede) {
        hospedeDAO.insert(hospede);
    }

    public List<Hospede> find() {
        return hospedeDAO.find(null);
    }
    
    public List<Hospede> findByIdReserva(Long reservaId) {
        return hospedeDAO.findByReservaId(reservaId);
    }

    public void update(Hospede hospede) {
        hospedeDAO.update(hospede);
    }

    public void delete(Hospede hospede) {
        hospedeDAO.delete(hospede);
    }
    
    public List<Hospede> buscarPorSobrenome(String sobrenome) {
        return hospedeDAO.buscarPorSobrenome(sobrenome);
    }

}
