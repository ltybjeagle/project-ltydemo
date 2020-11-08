// 异步编程：Promise模式
function doSomething() {
    let promise = new Promise();
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://wthrcdn.etouch.cn/weather_mini?city=%E5%8D%97%E4%BA%AC', true);
    xhr.onload = function (e) {
        if (this.status === 200) {
            results = JSON.parse(this.responseText);
            promise.resolve(results); //成功时，调用resolve()方法
        }
     };
    xhr.onerror = function (e) {
        promise.reject(e); //失败时，调用reject()方法
    };
    xhr.send();
    return promise;
}
// doSomething执行成功，调用promise.resolve，执行后续的then
// 执行失败的话，执行promise.reject，直接跳到catch执行
doSomething()
    .then(result => doSomethingElse(result))
    .then(newResult => doThirdThing(newResult))
    .then(finalResult => {
        console.log(`Got the final result: ${finalResult}`);
    }).catch(failureCallback);

async function foo() {
    try {
        let result = await doSomething();  // await等待promise结果，promise.resolve返回结果，promise.reject调转异常处理
        let newResult = await doSomethingElse(result);
        let finalResult = await doThirdThing(newResult);
        console.log(`Got the final result: ${finalResult}`);
    } catch(error) {
        failureCallback(error);
    }
}