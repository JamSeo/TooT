export interface Ibubble {
  type?: string | undefined;
  message: string;
  url?: string | undefined;
  speaker: boolean;
};

export interface IchatResponseData {
  userId: string,
  version: string,
  timestamp: number,
  event: string,
  content: {
    type: string,
    title: string,
    data: {
      details: string,
      description: string | undefined,
      url: string | undefined,
    },
  },
};