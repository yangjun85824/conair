import {
  checkUrl
} from '@/utils/utils'
import axios from 'axios';
import {
  ElMessage
} from 'element-plus';

const HEADER_TOKEN_KEY = 'X-Authorization-access_token'; // header 请求的token key

const $website = window.$website
const {
  clientOption
} = $website
window.$glob = {
  url: '',
  group: '',
  themeId: '',
  theme: {},
  params: {},
  query: {},
  header: {}
}

const query = window.location.search.substring(1);
if (query !== '') {
  query.split('&').forEach(ele => {
    const pair = ele.split('=');
    window.$glob.params[pair[0]] = pair[1];
  });
}

const token = window.$glob.params[clientOption.accessToken];
if (token) {
  localStorage.setItem(clientOption.accessToken, token);
}


axios.defaults.timeout = 20000;
axios.defaults.validateStatus = status => status >= 200 && status <= 500;

axios.interceptors.request.use(config => {
  let url = config.url;
  const globParams = url.match(/\$\{(.+?)\}/g) || [];
  globParams.forEach(ele => {
    const key = ele.replace('${', '').replace('}', '');
    url = url.replace(ele, window.$glob[key]);
  });
  config.url = url;
  config.headers[HEADER_TOKEN_KEY] = "f10f6b1055f611ef2b24126227bf5553";
  if (!checkUrl(config.url)) {
    config.url = window.$glob.url + config.url;
  }

  const header = window.$glob.header || {};
  config.headers = {
    ...config.headers,
    ...header
  };

  const token = localStorage.getItem(clientOption.accessToken);
  if (token) {
    config.headers[clientOption.authorization] = `Basic ${btoa(`${clientOption.clientId}:${clientOption.clientSecret}`)}`;
    config.headers[clientOption.tokenHeader] = `${clientOption.bearer} ${token}`;
  }

  const data = window.$glob.query || {};
  let key;
  if (['get', 'delete'].includes(config.method)) {
    key = 'params';
  } else if (['post', 'put'].includes(config.method)) {
    key = 'data';
  }
  if (config[key] && typeof config[key] === 'object') {
    config[key] = Object.assign(config[key] || {}, data)
  }

  if (config.headers.proxy) {
    const headers = {};
    for (const ele in config.headers) {
      if (typeof config.headers[ele] !== 'object') {
        headers[ele] = config.headers[ele];
      }
    }
    const form = {
      url: config.url,
      method: config.method,
      headers,
    };
    form[key] = config[key];
    config.url = $website.url + '/visual/proxy';
    config.method = 'post';
    config.data = form;
  }

  return config;
}, error => {
  return Promise.reject(error);
});

axios.interceptors.response.use(config => {
  const status = config.status;
  if (status !== 200) {
    ElMessage({
      message: config.data.msg || `${config.status} ${config.statusText}`,
      type: 'error',
    });
    return Promise.reject(new Error(config.data.msg));
  }
  return config;
}, error => {
  return Promise.reject(new Error(error));
});

export default axios;