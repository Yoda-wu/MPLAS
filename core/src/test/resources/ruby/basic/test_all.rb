puts 'hahaha!'
puts a

if 'a' > 'b'
  puts "a > b"
elsif 'a' < 'b'
  puts "a < b"
end

if 'a' > 'b'
  puts "a > b"
elsif 'c' > 'd'
  puts "c > d"
elsif 'c' < 'd'
  puts "c < d"
end

unless 'a' < 'b'
  puts "a > b"
  elsif 'c' > 'd'
  puts "c > d"
else
  puts "c < d"
end

mas = []
mas = read_file("lab4.txt")

length = len(mas)

for(i = 1; i < length - 1; i+=1)
for(j = 0; j < length - i; j+=1)
if(mas[j] > mas[j+1])
  buf = mas[j]
  mas[j] = mas[j+1]
  mas[j+1] = buf
end
end
end

for(i = 0; i < length; i+=1)
puts mas[i]
end


s = "hello"
l = len(s)
puts l

a = func(1,2)
puts a

def func a, b
  return a+b
end