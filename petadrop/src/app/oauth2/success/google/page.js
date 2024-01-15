'use client';

import { setCookie, getCookie } from "@/utils/cookie";
import { useEffect } from "react";
import { useSearchParams } from "next/navigation";
import axios from 'axios';
// import { useCookies } from 'next-client-cookies';
// import { cookies } from 'next/headers'
// import { toSvg } from 'html-to-image';
// import client from "@/axios/client";
// import ItemView from "@/components/item/ItemView";

axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.headers.common['Authorization'] = `Bearer ${getCookie("accessToken")}`;

function test(){
  console.log(getCookie("accessToken"));
  axios.get("",{
  }).then((res) => {
    console.log(res);
  }).catch((err) => {
    console.log(err);
  })

}

export default function Home() {
    const params = useSearchParams();
    // console.log(params);
    useEffect(() => {
      for (const [key, value] of params.entries()) {
          console.log(`${key}, ${value}`);
          if(key === "accessToken"){
            setCookie("accessToken", value, { path: '/', secure: true });
          }
          if(key === "refreshToken"){
            setCookie("refreshToken", value, { path: '/', secure: true });
          }
      }
      test();
    });
    return (
      <main>
        콘솔을 확인해주세요.
      </main>
    )
  }
