import * as Styled from "./style";
import Button from "@src/components/@shared/Button";
import { FlexRow } from "@src/@styles/shared";

function Home() {
  return (
    <Styled.Container>
      <Styled.GreetingContainer>
        <FlexRow
          gap="10px"
          marginBottom="27px"
          justifyContent="center"
          alignItems="center"
          flexWrap="wrap"
        >
          <img src="" alt="줍줍 로고 이미지" />
          <h2>
            사라지는 슬랙 메시지,
            <br />
            우리가 주워줄게!
          </h2>
        </FlexRow>
        <Button>시작하기</Button>
      </Styled.GreetingContainer>

      <Styled.UsageContainer>
        <h1>이용 방법</h1>
        <Styled.UsageList>
          <li>
            워크스페이스에 줍줍 Slack App 을 설치하고 백업하고 싶은 채널에
            초대해주세요 🤗
          </li>
          <li>이제부터 여러분의 대화를 줍줍이가 보관해드릴거예요 🤚</li>
          <li>사이트에 방문하셔서 대화를 확인해보세요 😎</li>
        </Styled.UsageList>
      </Styled.UsageContainer>
    </Styled.Container>
  );
}

export default Home;
