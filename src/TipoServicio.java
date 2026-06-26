
public class TipoServicio {
    
    private String tipoServicio;
    private double costo;
    private boolean servicioTerminado;

    public TipoServicio(String tipoServicio, double costo,boolean servicioTerminado) {
            this.tipoServicio = tipoServicio;
            this.costo = costo;
            this.servicioTerminado = servicioTerminado;
    }
    
    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isServicioTerminado() {
        return servicioTerminado;
    }

    public void setServicioTerminado(boolean servicioTerminado) {
        this.servicioTerminado = servicioTerminado;
    }
}