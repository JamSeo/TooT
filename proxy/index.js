const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const axios = require("axios");
const OpenAI = require("openai");
require("dotenv").config();

const app = express();
app.use(cors({
    origin: [
        "http://localhost:3000",
        "https://too-t.com"
    ]
}));

app.use(bodyParser.json());

app.post("/chatbot", async (req, res) => {
    const sendData = req.body.sendData;
    const chatbotDomainId = process.env.CHATBOT_DOMAIN_ID;
    const chatbotDomainKey = process.env.CHATBOT_DOMAIN_KEY;
    try {
        const response = await axios.post(`https://clovachatbot.ncloud.com/api/chatbot/messenger/v1/${chatbotDomainId}/${chatbotDomainKey}/message`, sendData);
        res.json({ chatResponse: response.data });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.post("/chatgpt", async (req, res) => {
    const openai = new OpenAI({
        apiKey: process.env.OPEN_API_KEY
    });
    const sendData = [{
        "role": "user",
        content: req.body.sendData + " 한국어로 답변해줘" + "만약 내가 하는 질문에 동의어가 있다면 주식이나 금융, 투자 관련으로 생각해서 답변해줘"
    }];
    try{
        const response = await openai.chat.completions.create({
            model: "gpt-3.5-turbo",
            messages: sendData,
        });
        res.json({ chatResponse: response });
    } catch (err) {
        console.log(err);
        res.status(500).json({ error: error.message });
    }
});

const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
    console.log(`Express server listening on port ${PORT}`);
});
