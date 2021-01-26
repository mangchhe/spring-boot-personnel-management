package team.okky.personnel_management.utils.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class PageResultDTO {

    private int totalCnt;
    private int startPage;
    private int endPage;
    private int totalPage;

    public PageResultDTO(int totalCnt, int curPage){
        this.totalCnt = totalCnt;
        this.totalPage = (int) Math.ceil((totalCnt / 10.0));
        this.endPage = (int) (Math.ceil(curPage / 10.0) * 10);
        this.startPage = endPage - 9 < 0 ? 1 : endPage - 9;
        this.endPage = Math.min(this.endPage, this.totalPage);
    }
}
