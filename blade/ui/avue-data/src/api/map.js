import {
  url
} from '@/config';
import request from '../axios'
const baseUrl = url + '/map'
export const getList = (params) => request({
  url: baseUrl + '/lazy-list',
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
  url: baseUrl + '/save',
  method: 'post',
  data: data
});
export const updateObj = (data) => request({
  url: baseUrl + '/update',
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

export const getMapData = (id) => request({
  url: baseUrl + '/data',
  method: 'get',
  params: {
    id: id
  }
});