package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class) //내가 만든 클래스는 import 해줘야 함.
@DataJpaTest // DB 관련 객체들이 IoC에 뜬다.
public class BoardRepositoryTest {

    @Autowired // Test에서 DI 하는 코드
    private BoardRepository boardRepository;

    @Test
    public void delete(){
        //given(객체의 바뀔 타겟점)
        int id = 1;

        //when
        boardRepository.delete(id);

        //then(상태)
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());
        Assertions.assertThat(boardList.get(0).getId()).isEqualTo(2);//결과 값 (삭제 하고나서 몇 번째 id가 시작점인지)
        Assertions.assertThat(boardList.size()).isEqualTo(7);

    }

    @Test
    public void update(){
        //given(객체의 바뀔 타켓점)
        String title = "새로운 제목";
        String content = "새로운 내용";
        int id =1;
        //when
        boardRepository.update(title,content,id);
        //then(상태)
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());
        Assertions.assertThat(boardList.get(0).getTitle()).isEqualTo("새로운 제목");
        Assertions.assertThat(boardList.get(0).getContent()).isEqualTo("새로운 내용");
    }
    @Test
    public void selectAll_test(){
        // given

        // when
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());

        // then (id=1, title=제목1, content=내용1, author=홍길동)
        // System.out.println(boardList);
        Assertions.assertThat(boardList.get(0).getTitle()).isEqualTo("제목1");
        Assertions.assertThat(boardList.get(0).getContent()).isEqualTo("내용1");
        Assertions.assertThat(boardList.get(0).getAuthor()).isEqualTo("홍길동");
        Assertions.assertThat(boardList.size()).isEqualTo(8);
    }
    @Test
    public void selectOne_test(){
        //given
        int id = 1;

        //when
       Board board = boardRepository.selectOne(id);

        //then(상태 검사)
        //System.out.println(board);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");//실제로 실행 했을 때 리턴된 값을 넣어야됨
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
        Assertions.assertThat(board.getAuthor()).isEqualTo("홍길동");
    }
    @Test
    public void insert_test(){ //테스트 메서드는 파라미터가 없다. 리턴도 없다.
        // given
        String title = "제목10";
        String content = "내용10";
        String author = "이순신";

        // when
        boardRepository.insert(title, content, author);

        // then -> 눈으로 확인 (쿼리)
    } // Rollback (자동)
}