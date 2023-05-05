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
    puts "c > d"
else
  puts "c < d"
end

mas = []
mas = read_file("lab4.txt")

length = len(mas)

for i in [1...5]

  puts i

end

s = "hello"
l = len(s)
puts l

a = func(1,2)
puts a

def func a, b
  return a+b
end
