export interface IChatResponseData {
  userId: string,
  version: string,
  timestamp: Date,
  event: string,
  content:[{
    type: string,
    title?: string,
    data:{
      details?: string,
      description?: string,
      url?: string
    },
  }],
};