import {
  url
} from '@/config';
import request from '../axios'
const baseUrl = url + '/ai'
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

export const add = (data) => request({
  url: baseUrl + '/save',
  method: 'post',
  data: data
});
export const update = (data) => request({
  url: baseUrl + '/update',
  method: 'post',
  data: data
});



export const remove = (id) => request({
  url: baseUrl + '/remove',
  method: 'post',
  params: {
    ids: id
  }
});