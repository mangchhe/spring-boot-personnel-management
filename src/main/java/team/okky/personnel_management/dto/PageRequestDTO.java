package team.okky.personnel_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    private int page = 1;
    private int size = 10;

    public PageRequestDTO(int page){
        this.page = page;
    }

    public int getPage(){
        return (page - 1) * size;
    }

    public int getSize(){
        return size;
    }

}
