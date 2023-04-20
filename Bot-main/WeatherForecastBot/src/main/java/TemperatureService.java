import java.io.IOException;
import java.time.LocalDateTime;

public class TemperatureService {
    private final int actualizationPeriodInHours = 1;
    private LocalDateTime lastActualizationMoment;
    private final DbHandler dbHandler;
    private final TemperatureLoader temperatureLoader;

    public TemperatureService() throws Exception {
        temperatureLoader = new TemperatureLoader();
        dbHandler = TryCreateDbHandler();
     }

     private DbHandler TryCreateDbHandler() throws Exception {
         try {
             return new DbHandler();
         } catch (Exception exception) {
             throw new Exception("Could not create connection to db " + exception);
         }
     }

     public Temperature GetTemperature() {
         LocalDateTime now = LocalDateTime.now();

        if (lastActualizationMoment == null || now.isAfter(lastActualizationMoment.plusHours(actualizationPeriodInHours))){
            Temperature temperature = UpdateTemperatureInDb();

            lastActualizationMoment = now;

            return temperature;
        }

         return dbHandler.GetTemperature();
     }

     private Temperature UpdateTemperatureInDb(){
         Temperature temperature;

         try {
             temperature = temperatureLoader.Load();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }

         dbHandler.SaveTemperature(temperature);

         return temperature;
     }
}
