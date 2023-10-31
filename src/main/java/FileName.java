import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileName {
    public String getPath(){
        String path = String.valueOf(LocalDateTime.now().getYear()).concat("\\").concat(LocalDateTime.now().getMonth().name())
                .concat("\\").concat(String.valueOf(LocalDateTime.now().getDayOfMonth())).concat("\\");
        return path;
    }
    public String createFileName(String code, String name){
        String fileName = "Zim_";
        fileName = fileName.concat(code).concat("_").concat(name.trim().replaceAll(" ","_")).concat("_");
        fileName = fileName.concat(dateTimeFomarter());
        return fileName;
    }

    private String dateTimeFomarter(){
        LocalDateTime dt = LocalDateTime.now();
        return dt.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss"));
    }
}
