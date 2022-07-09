import ReactDOM from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import { LIGHT_MODE_THEME } from "@src/@styles/theme";
import Home from "@src/pages/Home";
import LayoutContainer from "@src/components/layouts/LayoutContainer";
import GlobalStyle from "./@styles/GlobalStyle";
import { QueryClientProvider, QueryClient } from "react-query";

if (process.env.NODE_ENV === "development") {
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const { worker } = require("./mocks/browser");
  worker.start();
}

const root = ReactDOM.createRoot(document.getElementById("root") as Element);

const queryClient = new QueryClient();

root.render(
  <BrowserRouter>
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={LIGHT_MODE_THEME}>
        <GlobalStyle />
        <LayoutContainer>
          <Routes>
            <Route path="/" element={<Home />} />
          </Routes>
        </LayoutContainer>
      </ThemeProvider>
    </QueryClientProvider>
  </BrowserRouter>
);
