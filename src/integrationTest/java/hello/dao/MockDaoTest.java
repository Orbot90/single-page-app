package hello.dao;

import hello.model.MockEntity;
import hello.repository.MockRepository;
import hello.testconfig.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orbot on 28.05.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class MockDaoTest {

    @Autowired
    private MockRepository mockRepository;

    @Test
    public void test() {
        List<MockEntity> mocks = new ArrayList<>();
        MockEntity mock1 = new MockEntity();
        mock1.setSomeParam("12");
        mocks.add(mock1);
        MockEntity mock2 = new MockEntity();
        mock2.setSomeParam("100");
        mocks.add(mock2);
        mockRepository.save(mocks);
        Long maxParam = mockRepository.getMaxSomeParam();
        System.out.println(maxParam);
    }
}
