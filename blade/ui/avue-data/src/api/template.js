import {
  url
} from '@/config';
import request from '../axios'
const baseUrl = url + '/push/template'
export const getList = (params) => request({
  url: baseUrl + '/list',
  method: 'get',
  params: params
});



export const getObj = (id) => request({
  url: baseUrl + '/detail',
  method: 'get',
  params: {
    id
  }
});

export const addObj = (data) => request({
  url: baseUrl + '/submit',
  method: 'post',
  data: data
});
export const updateObj = (data) => request({
  url: baseUrl + '/submit',
  method: 'post',
  data: data
});



export const delObj = (id) => request({
  url: baseUrl + '/remove',
  method: 'post',
  params: {
    ids: id
  }
});

export const debug = (code, params) => {
  return request({
    url: baseUrl + '/debug',
    method: 'post',
    params: {
      code,
      params
    }
  })
}