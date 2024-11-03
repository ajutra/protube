import { act, render } from "@testing-library/react";
import "@testing-library/jest-dom";
import { setupServer } from "msw/node";
import { http, HttpResponse } from "msw";

jest.mock("../../../src/utils/Env.ts");

import VideoGrid from "../VideoGrid";
import { MemoryRouter } from "react-router-dom";

const server = setupServer(
  http.get("/api/someEndpoint", () => {
    return HttpResponse.json([
      {
        id: 23,
        title: "The Cranberries - Zombie (Alt. Version)",
        author: "TheCranberriesTV",
      },
    ]);
  })
);

describe("this component", () => {
  beforeAll(() => {
    server.listen();
  });

  // reset any request handlers that are declared as a part of our tests
  // (i.e. for testing one-time error scenarios)
  afterEach(() => server.resetHandlers());
  // clean up once the tests are done
  afterAll(() => server.close());

  it("renders correctly", async () => {
    const asFragment = await act(async () => {
      return render(
        <MemoryRouter>
          <VideoGrid />
        </MemoryRouter>
      ).asFragment;
    });
    expect(asFragment()).toMatchSnapshot();
  });
});
