package hyx.plateform.model;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class Result {
    
    public String subCaseName;
    
    public String average;
    
    public String standardDev;

    public String ratio;

    public String time;

    
}
