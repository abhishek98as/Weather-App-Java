import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {

    // API
    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        try {
            String weatherData = getWeatherDataFromAPI();

            if (weatherData == null) {
                System.out.println("Unable to fetch weather data");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("\n1. Get weather\n2. Get Wind Speed\n3. Get Pressure\n0. Exit");
                System.out.print("Enter your choice: ");
                String option = reader.readLine();

                switch (option) {
                    case "1":
                        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
                        String date1 = reader.readLine();
                        double temp = getTempForDate(weatherData, date1);
                        if (temp != Double.MIN_VALUE) {
                            System.out.printf("Temperature on %s is %.2f Kelvin.%n", date1, temp);
                        } else {
                            System.out.println("No data available for the given date.");
                        }
                        break;

                    case "2":
                        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
                        String date2 = reader.readLine();
                        double windSpeed = getWindSpeedForDate(weatherData, date2);
                        if (windSpeed != Double.MIN_VALUE) {
                            System.out.printf("Wind Speed on %s is %.2f m/s.%n", date2, windSpeed);
                        } else {
                            System.out.println("No data available for the given date.");
                        }
                        break;

                    case "3":
                        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
                        String date3 = reader.readLine();
                        double pressure = getPressureForDate(weatherData, date3);
                        if (pressure != Double.MIN_VALUE) {
                            System.out.printf("Pressure on %s is %.2f hPa.%n", date3, pressure);
                        } else {
                            System.out.println("No data available for the given date.");
                        }
                        break;

                    case "0":
                        System.out.println("Exiting the program.");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading user input.");
        }
    }

    private static String getWeatherDataFromAPI() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            return null;
        }
    }
    private static double getTempForDate(String weatherData, String date) {
        return 288.15; // Equivalent to 15 degrees Celsius (default value)
    }
    private static double getWindSpeedForDate(String weatherData, String date) {
        return 4.5; // m/s (default value)
    }
    private static double getPressureForDate(String weatherData, String date) {
        return 1013.25; // hPa (default value)
    }
}
