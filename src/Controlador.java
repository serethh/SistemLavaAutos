/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Controlador {

    private ArrayList<Auto> autos = new ArrayList<>();

    public void guardarAuto(Auto auto) {
        autos.add(auto);
    }

    public Auto buscarPlaca(String placas) {
        for (Auto a : autos) {
            if (a.getPlacas().equalsIgnoreCase(placas)) {
                return a;
            }
        }
        return null;
    }

    public boolean existePlaca(String placas) {
        return buscarPlaca(placas) != null;
    }


    public double obtenerCosto(String servicio) {
       switch (servicio) {
        case "Lavado exterior":
            return 80;

        case "Lavado interior":
            return 100;

        case "Lavado completo":
            return 180;

        case "Encerado":
            return 250;

        case "Pulido":
            return 350;

        case "Aspirado":
            return 60;

        case "Lavado de motor":
            return 150;

        case "Detallado":
            return 500;

        default:
            return 0;
      }
    }

    public boolean registrarServicio(String placas,String servicio,boolean terminado) {
        Auto auto = buscarPlaca(placas);
        if (auto == null) {
            return false;
        }
        auto.setTipoServicio(servicio);
        auto.setCosto(obtenerCosto(servicio));
        auto.setServicioTerminado(terminado);
        if (terminado) {
            auto.setEstado("Terminado");
        } else {
            auto.setEstado("En proceso");
        }
        return true;
    }

    public boolean registrarEgreso(String placas,String hora,String observaciones,String pago) {
        Auto auto = buscarPlaca(placas);
        if (auto == null) {
            return false;
        }
        if (!auto.isServicioTerminado()) {
            return false;
        }
        auto.setHoraEgreso(hora);
        auto.setObservaciones(observaciones);
        auto.setTipoPago(pago);
        auto.setEstado("Entregado");
        return true;

    }

    public ArrayList<Auto> obtenerAutos(){
        return autos;
    }
        
    public void cambiarEstado(String placas,String estado){
        Auto a = buscarPlaca(placas);
        if(a!=null){
            a.setEstado(estado);
        }
    }
        
    public Auto obtenerAuto(String placas){
        return buscarPlaca(placas);
    }
        
    public boolean servicioTerminado(String placas){
        Auto a = buscarPlaca(placas);
        if(a==null){
            return false;
        }
        return a.isServicioTerminado();
    }

    public double obtenerCostoVehiculo(String placas){
        Auto a = buscarPlaca(placas);
        if(a==null){
            return 0;
        }
        return a.getCosto();
    }
    
    public String obtenerServicio(String placas){
        Auto a = buscarPlaca(placas);
        if(a==null){
            return "";
        }
        return a.getTipoServicio();
    }
    
    public void llenarTabla(DefaultTableModel modelo){

        modelo.setRowCount(0);

        for(Auto auto : autos){

            modelo.addRow(new Object[]{
                auto.getNombre(),
                auto.getApellidoPaterno(),
                auto.getApellidoMaterno(),
                auto.getTelefono(),
                auto.getTipoVehiculo(),
                auto.getColor(),
                auto.getPlacas(),
                auto.getModelo(),
                auto.getFechaIngreso(),
                auto.getHoraIngreso(),
                auto.getTipoServicio(),
                auto.getCosto(),
                auto.isServicioTerminado() ? "Sí" : "No",
                auto.getHoraEgreso(),
                auto.getTipoPago(),
                auto.getObservaciones(),
                auto.getEstado()
            });

        }
    }
    
    public void buscarAuto(String placas, DefaultTableModel modelo){

        modelo.setRowCount(0);

        for(Auto auto : autos){

            if(auto.getPlacas().toUpperCase().contains(placas.toUpperCase())){

                modelo.addRow(new Object[]{
                    auto.getNombre(),
                    auto.getApellidoPaterno(),
                    auto.getApellidoMaterno(),
                    auto.getTelefono(),
                    auto.getTipoVehiculo(),
                    auto.getColor(),
                    auto.getPlacas(),
                    auto.getModelo(),
                    auto.getFechaIngreso(),
                    auto.getHoraIngreso(),
                    auto.getTipoServicio(),
                    auto.getCosto(),
                    auto.isServicioTerminado() ? "Sí" : "No",
                    auto.getHoraEgreso(),
                    auto.getTipoPago(),
                    auto.getObservaciones(),
                    auto.getEstado()
                });

            }

        }
    
    }
    public ArrayList<String> buscarCoincidencias(String placas){

        ArrayList<String> lista = new ArrayList<>();

        for(Auto auto : autos){

            if(auto.getPlacas().toUpperCase().contains(placas.toUpperCase())){

                lista.add(auto.getPlacas());

            }

        }

        return lista;

    }
    
    public void actualizarServicio(String placa,String servicio,boolean terminado,String observaciones){

        Auto auto = obtenerAuto(placa);

        if(auto != null){

            auto.setTipoServicio(servicio);

            auto.setCosto(obtenerCosto(servicio));

            auto.setServicioTerminado(terminado);

            auto.setObservaciones(observaciones);

            if(terminado){

                auto.setEstado("Terminado");

            }else{

                auto.setEstado("En proceso");

            }

        }

    }
}
