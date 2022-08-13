package com.yyld.conair.pay.constants;

/**
 * @ClassName PayConstants
 * @Description //TODO
 * @Author yangj
 * @Date 2022/8/12 11:20
 * @Vresion 1.0
 **/
public final class PayConstants {

    /*
    URL

支付宝网关（固定）。

https://openapi.alipay.com/gateway.do
APPID

APPID 即创建小程序后生成。

获取可查看 获取 APPID。

PRIVATE_KEY

开发者私钥，由开发者自己生成。

获取可查看 接口加签方式。

FORMAT

参数返回格式，只支持 JSON（固定）。

JSON

CHARSET

编码集，支持 GBK/UTF-8。

开发者根据实际工程编码配置。

ALIPAY_PUBLIC_KEY

支付宝公钥，由支付宝生成。

获取详情可查看 接口加签方式。

SIGN_TYPE

生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2。
     */
    public final static String ALIPAY_APPID = "2016120103696853";
    public final static String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
    // 应用公钥 //MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqlhXUniLdwYR8/nYiWar4ygPpCSZyqkeE9YG7g0eqsrSkEhN5QfkvGQ01D2iqi2ELh1dPBbIp06E2iuPxz3y2y48DUkJX2ZkVNsjVFehsWFf0U139Aj2Bbj1hioIzWGRnDpjoEByFwv15rvzpJIfGQzzIroFGRntMUwxtsE+NOjbn7vxy9RqzVKenhaYguiRGQttR0D+HQCnY2R4jFN3rAgbIb+TrtMF4DvzOdpXj8J5OAY/v9DJspdncCBDgQ2m/vF5P9jQw89yZPgOfUu3AHhLXM0vG8CrUXwsYhdx8+Gj4pmuus8SLZu5L1S2fGbARzQiU7mHwu98W33jTv0CeQIDAQAB
    public final static String PRIVER_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCK+iOQ2zv8Q224QRLY2fqtylWJzaEd29U9LQiy7hNPOm7DwZuBGQNcQQyAwSf0dlpGPcrSszhboaY1qCq1ansx6TfO4RYyElyO4A2GjjfZnIEf0c7ezTY6BcnLTDGXu8q5w17XVi637g5jM8NP74bkfaMnbEpqlertVAvCLnygNDjPlWHgGBY9sdXIszjA5bMtfzYqSQ2zyoys2zty7vz4rzs8DpOO14uHXFrDkgCuzhpc/jIUR9HhjDIs4BwndsYY3+WOBM0LxagjES9SIazKmXjrMXyRdCU+7V2bWsUCpilcxEKYwvWi9qcqSWguVuOjvAvBmbdns3Rha+P6BxPAgMBAAECggEBAIS7fz3A2bcsETPynfzq9x58FsXOCHaFLpXksT8YA+TIvv4gR0Ttu3d0ymllpsOZsAUoC86MU4HHDDtufGZLqN0znID9LnuCbrKn+f0B797HKSnp18fRcU7VWP5u5ei0/MlXKOAr73vPcx/kIaXs7Qfrv55gLbtNtqCqUSXV0Ubzld0arNiLOs6p/ZokEP3zwFYMUSO2T97hCOQ0Y9y1nk/wM2j76euQCrSjoUECnlYjV27LSnoBLrlWlKOT1dDgWV1cZQoYctub8oXFgAW4w2DAE1NrEP9Ewv1GBvbuZL8isHO3i4AMW5hE2WXRJi4kp1hHIXimGe3Xy0ZaiwYeyAECgYEA80d4/QltpMSeMGE97q5y4yJr0BrsdR/s1F8cFmEbAAYiK32aPIEF4bkd2IE55H3PH701f64UZuS/Q/2nDE6t08UmQBog7WidqyRk0vnL5RposIrd/+peJK65Fg/9Jx/2RjY9qmG7K8KVdDmezOoZHxqSWbdJGpawnqDjhjmlV+0CgYEAzFMVA7hEW/rmyE7vl8s2YVLPzWGI0s7strX73QjjOypgu1kfZFd7wkgd05WCp8QuZV6DoaqgsXf1SnMLwo0l6bcjRMAgrHI/yOpHmBl4jm8LpptgfEfH12hnK3xxNFVRH9Z6KOaw3DYnfNE+NZvd60gzewpY/G/41FIQJGVhxasCgYAwiCeYwxK+dgLgv+VtAhwlN66uW1CkpPPIAhlGVaTfQln6txnXzB1yRE2x7UWxUW/KcyKDECU9yZBmbemvSNrgjkntK8k5007w31fFxtNoJvaPkYRsE0Moqjp+Lj5gWb31j8iJfump9y19fCkz09ejHtfnUeshhMJDQvFgy4symQKBgBQWWuuJsxbD3Op2cDGhJdB9nrZXT2dCNp1j2MYLOKO5b9cbVO29eApKorwJZ9XsgI8wsoozRycLXw8Zrl9LFx4aBhqdNNkfRkwPVEiMwIBoRGH1xo+RUwFHX1U0H8EAXkBeCpZ4z4xmuo0X2SDWGQQnjgAbrVHsBC+DfdjGKs2PAoGAUw4bLWxTR5K5Zhb1VRfHwzbXxz8S5gNnEfljjqCrIuXOGdYeJzKr3IwQhAnUdOWFqD/HKj6HzDOPJGqhtWqHBjdIEUW/Vg4ucS38Is25G1Mk0Sk/xEm0zRiuTtb5JgJKfYDMzF0Bglb4cMPssMI9S3xs+tM1nKt051ffL2cGNAs=";
    public final static String ALIPAY_FORMAT = "JSON";
    public final static String ALIPAY_CHARSET = "UTF-8";
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiDPPzYHyd/O8dDFsLE/DWkhKXaAiI/ILn+kTCnqN1i7F5qjDmnlOz01cDImU9QfoR+EIkmXqiGg2gsRBp/TQ2F4+KXhiGMZRT9eLQXb+51GUo/6OdtZBFXMLaNYTrcGVHvymZB5diBVeH3ZoqEB97vQgOYIZncJvnXohmmmsRry1jEjrZyLVdmweX8D27n2Liztdv3copHuTL5GAZH1y1Ppmc+OuBYAtnGfUgucLi9khVU3VBM/KZr52APrjzYcnFjDV6+y1KJyV28ZD8Z0S/b+2PNBV0EPeOcML7YgYQVRz8U7e9vJjEnxgZddgyJkj6TT2DkyMsM+em5OpXqhKbQIDAQAB";
    public final static String ALIPAY_SIGN_TYPE = "RSA2";

    public final static String app_cert_path = "D:/pay/alipay/CSR/www.yiyld.com.csr";
    public final static String alipay_cert_path = "D:/pay/alipay/CSR/www.yiyld.com_公钥.txt";
    public final static String alipay_root_cert_path = "D:/pay/alipay/CSR/www.yiyld.com_私钥.txt";
}
