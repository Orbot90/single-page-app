package hello.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by orbot on 28.05.16.
 */
@Entity
@Data
public class MockEntity {
    @Id
    @SequenceGenerator(name = "genMock", sequenceName = "seq_gen_mock")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String someParam;
}
