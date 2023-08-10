package main

import (
	"errors"
	"fmt"
	"reflect"
	"strconv"
	"strings"
	"testing"
)

func UnmarshalInt(data []byte, v interface{}) error {
	s := string(data)
	//去除前后的连续空格
	s = strings.TrimLeft(s, " ")
	s = strings.TrimRight(s, " ")
	typ := reflect.TypeOf(v)
	value := reflect.ValueOf(v)
	if typ.Kind() != reflect.Ptr {
		return fmt.Errorf("must use ptr arg")
	}
	typ = typ.Elem()

	if i, err := strconv.ParseInt(s, 10, 64); err != nil {
		return err
	} else {
		value.Elem().SetInt(i)
	}
	return nil
}

func UnmarshalFloat(data []byte, v interface{}) error {
	s := string(data)
	//去除前后的连续空格
	s = strings.TrimLeft(s, " ")
	s = strings.TrimRight(s, " ")
	typ := reflect.TypeOf(v)
	value := reflect.ValueOf(v)
	if typ.Kind() != reflect.Ptr {
		return fmt.Errorf("must use ptr arg")
	}
	typ = typ.Elem()

	if f, err := strconv.ParseFloat(s, 64); err != nil {
		return err
	} else {
		value.Elem().SetFloat(f)
	}
	return nil
}

func UnmarshalString(data []byte, v interface{}) error {
	s := string(data)
	//去除前后的连续空格
	s = strings.TrimLeft(s, " ")
	s = strings.TrimRight(s, " ")
	typ := reflect.TypeOf(v)
	value := reflect.ValueOf(v)
	if typ.Kind() != reflect.Ptr {
		return fmt.Errorf("must use ptr arg")
	}
	typ = typ.Elem()

	if s[0] == '"' && s[len(s)-1] == '"' {
		value.Elem().SetString(s[1 : len(s)-1])
	} else {
		return fmt.Errorf("invalid json part: %s", s)
	}
	return nil
}

func UnmarshalSlice(data []byte, v interface{}) error {
	s := string(data)
	//去除前后的连续空格
	s = strings.TrimLeft(s, " ")
	s = strings.TrimRight(s, " ")
	if len(s) == 0 {
		return nil
	}
	typ := reflect.TypeOf(v)
	value := reflect.ValueOf(v)
	if typ.Kind() != reflect.Ptr {
		return errors.New("must use ptr arg")
	}

	typ = typ.Elem()
	switch typ.Kind() {
	case reflect.String:
		if s[0] == '"' && s[len(s)-1] == '"' {
			value.Elem().SetString(s[1 : len(s)-1])
		} else {
			return fmt.Errorf("invalid json part: %s", s)
		}
	case reflect.Float32,
		reflect.Float64:
		if f, err := strconv.ParseFloat(s, 64); err != nil {
			return err
		} else {
			value.Elem().SetFloat(f)
		}
	case reflect.Uint,
		reflect.Uint8,
		reflect.Uint16,
		reflect.Uint32,
		reflect.Uint64,
		reflect.Int,
		reflect.Int8,
		reflect.Int16,
		reflect.Int32,
		reflect.Int64:
		if i, err := strconv.ParseInt(s, 10, 64); err != nil {
			return err
		} else {
			value.Elem().SetInt(i)
		}
	case reflect.Slice:
		if s[0] == '[' && s[len(s)-1] == ']' {
			arr := strings.Split(s[1:len(s)-1], ",")
			if len(arr) > 0 {
				slice := reflect.ValueOf(v).Elem()
				slice.Set(reflect.MakeSlice(typ, len(arr), len(arr)))

				for i := 0; i < len(arr); i++ {
					eleValue := slice.Index(i)
					eleType := eleValue.Type()
					if eleType.Kind() != reflect.Ptr {
						eleValue = eleValue.Addr()
					}
					if err := UnmarshalSlice([]byte(arr[i]), eleValue.Interface()); err != nil {
						return err
					}
				}
			}
		} else {
			return fmt.Errorf("invalid json part: %s", s)
		}
	}
	return nil
}

func UnmarshalStruct(data []byte, v interface{}) error {
	s := string(data)
	//去除前后的连续空格
	s = strings.TrimLeft(s, " ")
	s = strings.TrimRight(s, " ")
	if len(s) == 0 {
		return nil
	}
	typ := reflect.TypeOf(v)
	value := reflect.ValueOf(v)
	if typ.Kind() != reflect.Ptr {
		return errors.New("must use ptr arg")
	}

	typ = typ.Elem()
	switch typ.Kind() {
	case reflect.String:
		if s[0] == '"' && s[len(s)-1] == '"' {
			value.Elem().SetString(s[1 : len(s)-1])
		} else {
			return fmt.Errorf("invalid json part: %s", s)
		}
	case reflect.Float32,
		reflect.Float64:
		if f, err := strconv.ParseFloat(s, 64); err != nil {
			return err
		} else {
			value.Elem().SetFloat(f)
		}
	case reflect.Int,
		reflect.Int8,
		reflect.Int16,
		reflect.Int32,
		reflect.Int64:
		if i, err := strconv.ParseInt(s, 10, 64); err != nil {
			return err
		} else {
			value.Elem().SetInt(i)
		}
	case reflect.Uint,
		reflect.Uint8,
		reflect.Uint16,
		reflect.Uint32,
		reflect.Uint64:
		if i, err := strconv.ParseUint(s, 10, 64); err != nil {
			return err
		} else {
			value.Elem().SetUint(i)
		}
	case reflect.Slice:
		if s[0] == '[' && s[len(s)-1] == ']' {
			arr := SplitJson(s[1 : len(s)-1])
			if len(arr) > 0 {
				slice := reflect.ValueOf(v).Elem()
				slice.Set(reflect.MakeSlice(typ, len(arr), len(arr)))
				for i := 0; i < len(arr); i++ {
					eleValue := slice.Index(i)
					eleType := eleValue.Type()
					if eleType.Kind() != reflect.Ptr {
						eleValue = eleValue.Addr()
					}
					if err := UnmarshalStruct([]byte(arr[i]), eleValue.Interface()); err != nil {
						return err
					}
				}
			}
		} else {
			return fmt.Errorf("invalid json part: %s", s)
		}
	case reflect.Struct:
		if s[0] == '{' && s[len(s)-1] == '}' {
			arr := SplitJson(s[1 : len(s)-1])
			if len(arr) > 0 {
				fieldCount := value.Elem().NumField()
				tag2Field := make(map[string]string, fieldCount)
				for i := 0; i < fieldCount; i++ {
					fieldType := typ.Field(i)
					name := fieldType.Name
					if len(fieldType.Tag.Get("json")) > 0 {
						name = fieldType.Tag.Get("json")
					}
					tag2Field[name] = fieldType.Name
				}

				for _, ele := range arr {
					brr := strings.SplitN(ele, ":", 2)
					if len(brr) == 2 {
						tag := strings.Trim(brr[0], " ")
						if tag[0] == '"' && tag[len(tag)-1] == '"' {
							tag = tag[1 : len(tag)-1]
							if fieldName, exists := tag2Field[tag]; exists {
								eleValue := value.Elem().FieldByName(fieldName)
								eleType := eleValue.Type()
								if eleType.Kind() != reflect.Ptr {
									eleValue = eleValue.Addr()
								}
								if err := UnmarshalStruct([]byte(brr[1]), eleValue.Interface()); err != nil {
									return err
								}
							} else {
								fmt.Printf("字段%s找不到\n", tag)
							}
						} else {
							return fmt.Errorf("invalid json part: %s", tag)
						}
					} else {
						return fmt.Errorf("invalid json part: %s", ele)
					}
				}
			}
		} else {
			return fmt.Errorf("invalid json part: %s", s)
		}
	default:
		fmt.Printf("暂不支持类型:%s\n", typ.Kind().String())
	}
	return nil
}

func TestUnmarshalPrimitive(t *testing.T) {
	json := "23"
	var i int
	if err := UnmarshalInt([]byte(json), &i); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(i)
	}

	json = "23.8"
	var f float32
	if err := UnmarshalFloat([]byte(json), &f); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(f)
	}

	json = "\"china\""
	var s string
	if err := UnmarshalString([]byte(json), &s); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(s)
	}
}

func TestUnmarshalSlice(t *testing.T) {
	json := "[1,2,3]"
	var arr []int
	if err := UnmarshalSlice([]byte(json), &arr); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(arr)
	}

	json = "[1.3,2.3,3.3]"
	var brr []float32
	if err := UnmarshalSlice([]byte(json), &brr); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(brr)
	}

	json = "[\"hello\",\"golang\"]"
	var crr []string
	if err := UnmarshalSlice([]byte(json), &crr); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(crr)
	}

	json = "[]"
	if err := UnmarshalSlice([]byte(json), &crr); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(crr)
	}
}

func TestUnmarshalStruct(t *testing.T) {
	json := `{"Name":"钱钟书","Age":57,"gender":1}`
	var user User
	if err := UnmarshalStruct([]byte(json), &user); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(user)
	}

	json = `{"isbn":"4243547567","Name":"围城","price":34.8,"author":{"Name":"钱钟书","Age":57,"gender":1},"kws":["爱情","民国","留学"],"Local":null}`
	var book Book
	if err := UnmarshalStruct([]byte(json), &book); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(book)
	}
}

//go test -v reflection/json/*.go -run=UnmarshalStruct
